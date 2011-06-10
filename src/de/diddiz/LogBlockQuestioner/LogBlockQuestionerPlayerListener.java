package de.diddiz.LogBlockQuestioner;

import java.util.Vector;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;

class LogBlockQuestionerPlayerListener extends PlayerListener
{
	private final Vector<Question> questions;

	LogBlockQuestionerPlayerListener(Vector<Question> questions) {
		this.questions = questions;
	}

	@Override
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (!event.isCancelled()) {
			final int playerHash = event.getPlayer().getName().hashCode();
			final int answerHash = event.getMessage().substring(1).toLowerCase().hashCode();
			for (final Question question : questions)
				if (question.isPlayerQuestioned(playerHash) && question.isRightAnswer(answerHash)) {
					question.returnAnswer(answerHash);
					questions.remove(question);
					event.setCancelled(true);
					break;
				}
		}
	}
}
