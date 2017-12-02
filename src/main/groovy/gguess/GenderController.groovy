package gguess

import io.swagger.annotations.ApiOperation
import org.jpmml.evaluator.ModelEvaluator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

@RestController
class GenderController {

    ModelEvaluator evaluator
    Map namesDb

    @Value('${max.list.size:1000}')
    private Integer maxListSize

    GenderController(ModelEvaluator evaluator, @Qualifier('namesDb') Map namesDb) {
        this.evaluator = evaluator
        this.namesDb = namesDb
    }

    @GetMapping('/')
    ResponseEntity<Map<String, String>> main(HttpServletRequest req) {
        def base = req.getRequestURL().toString()
        ResponseEntity.ok([documentation: base + 'swagger-ui.html',
                           health       : base + 'health',
                           info         : base + 'info',
                           message      : 'Gender Guess Service'])
    }

    @ApiOperation(value = "Show gender classification by name", notes = "Show gender classification by name")
    @GetMapping(value = '/guess/{name}', produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, String>> guess(@PathVariable String name) {
        char s = Utils.genderGuess(name, evaluator, namesDb)
        ResponseEntity.ok([name: name, gender: "$s".toString()])
    }

    @ApiOperation(value = "Show gender classification by name", notes = "Show gender classification by name")
    @PostMapping(value = '/guess', produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Map<String, String>>> batchGuess(@RequestBody List<String> names) {
        if (names.size() <= maxListSize) {
            def resp = names.collect { [name: it, gender: "${Utils.genderGuess(it, evaluator, namesDb)}".toString()] }
            ResponseEntity.ok(resp)
        } else throw new IllegalArgumentException("Number of names must be less than equal to ${maxListSize}")

    }
}
