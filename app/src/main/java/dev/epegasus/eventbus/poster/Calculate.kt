package dev.epegasus.eventbus.poster

import dev.epegasus.eventbus.models.Score
import org.greenrobot.eventbus.EventBus

class Calculate {

    fun calculateResult() {
        val score = Score("Sohaib", 10)
        EventBus.getDefault().post(score)
    }

}