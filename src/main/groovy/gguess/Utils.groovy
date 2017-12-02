package gguess

import groovy.transform.TypeChecked
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator
import org.dmg.pmml.FieldName
import org.jpmml.evaluator.ModelEvaluator
import org.jpmml.evaluator.VoteDistribution

import java.text.Normalizer

class Utils {

    @TypeChecked
    static String removeAccents(String word) {
        def first = word.split(/\s+/).first()
        def aux = Normalizer.normalize(first, Normalizer.Form.NFD)
        aux.replaceAll(/[^\p{ASCII}]/, "")
    }

    static String removeDuplicatedLetters(String word) {
        (word =~ "(\\w)\\1+").inject(word) { String acc, val -> acc.replaceAll(*val) }
    }

    @TypeChecked
    static String normName(String word) {
        word ? removeDuplicatedLetters(removeAccents(word.toLowerCase().trim())) : ''
    }

    @TypeChecked
    static double[] linspace(int init, int end, int size) {
        assert end > init
        double step = ((double) (end - init)) / (size - 1)
        (0..<size).collect { it * step } as double[]
    }

    @TypeChecked
    static double[] normInterpolate(String name, int flen = 10) {
        def y = name.collect { (((int) it) - (char) 'a') / (((char) 'z') - (char) 'a') }
        def x = (0..<name.size())
        def li = new LinearInterpolator() // or other interpolator
        def psf = li.interpolate(x as double[], y as double[])
        linspace(0, name.size() - 1, flen).collect { double it -> psf.value(it) } as double[]
    }

    @TypeChecked
    static double[] encodeString(String name, int flen = 10) {
        normInterpolate(name[-Math.min(flen, name.size())..-1])
    }

    @TypeChecked
    static char smartGuess(double[] word, ModelEvaluator evaluator) {
        def inputFields = evaluator.inputFields
        assert word.length == inputFields.size()
        def args = (0..<word.size()).collectEntries {
            [inputFields[it].name, inputFields[it].prepare(word[it] as double)]
        }
        def results = evaluator.evaluate(args) as Map<FieldName, VoteDistribution>
        evaluator.targetFields.collect { results.get(it.name).result == 0 ? 'M' : 'F' }.first() as char
    }

    @TypeChecked
    static char genderGuess(String name, ModelEvaluator evaluator, Map<String, Character> namesDb) {
        def word = normName(name)
        def vec = encodeString(word) as double[]
        (char) (namesDb.get(word) ?: smartGuess(vec, evaluator))
    }
}
