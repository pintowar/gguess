package gguess

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

import javax.servlet.http.HttpServletRequest

@Controller
class MainController {

    @GetMapping('/')
    ResponseEntity<Map<String, String>> main(HttpServletRequest req) {
        def base = req.getRequestURL().toString()
        ResponseEntity.ok([documentation: base + 'swagger-ui.html',
                           health       : base + 'health',
                           info         : base + 'info',
                           message      : 'Gender Guess Service'])
    }

}
