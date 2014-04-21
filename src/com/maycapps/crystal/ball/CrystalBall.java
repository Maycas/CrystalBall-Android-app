package com.maycapps.crystal.ball;

import java.util.Random;

public class CrystalBall {
	// Member variables (properties about the object)
	public String[] mAnswers;

	// Constructors	
	public CrystalBall(String[] answers) {
		mAnswers = answers;
	}

	// Methods (abilities: things the object can do)
	// Get a random answer
	public String getAnAnswer() {

		String answer = " ";

		// Randomly select one of the answers
		// Construct a random number using the Random class and the
		// nextInt method
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(mAnswers.length);

		// Assign a randomly generated number to an answer
		answer = mAnswers[randomNumber];

		// Return the result
		return answer;
	}
}
