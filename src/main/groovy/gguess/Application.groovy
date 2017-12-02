package gguess

import org.jpmml.evaluator.ModelEvaluator
import org.jpmml.evaluator.ModelEvaluatorFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import static org.jpmml.model.PMMLUtil.unmarshal

@EnableSwagger2
@SpringBootApplication
class Application {

    @Bean
    Docket api() {
        new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/guess/**"))
                .build()
    }

    @Bean
    ModelEvaluator genderGuessEvaluator() {
        def fis = new ClassPathResource('./GenderGuess.pmml').inputStream
        def pmml = unmarshal(fis)
        fis.close()
        def modelEvaluatorFactory = ModelEvaluatorFactory.newInstance()
        modelEvaluatorFactory.newModelEvaluator(pmml)
    }

    @Bean
    Map<String, Character> namesDb() {
        def f = new ClassPathResource('./names.csv').inputStream.text
                .split('\n')*.split(';')
        f.collectEntries { [Utils.normName(it[0]), it[1][0]] }
    }

    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}
