# Gender Guess

This project concept came from a common requirement of several clients which have legacy or social based databases. "Guess" their users gender (Male or Female) given only their name (especially thinking in brazilian names) as input data.

The main idea is to use the database to check the person gender, if it fails, it will use a trained neural network to try to guess the user gender.

This project uses Spring Boot and PMML to read a trained model. The model was trained using Python + Scikit Learn.

Ps.: This service was trained with common (and some uncommons) Brazilian names.


## Deploying on Heroku

To get your own Superset App running on Heroku, click the button below:

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/pintowar/gguess)

Fill out the form, and later you should be performing analytics at the speed of thought.
