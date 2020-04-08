package com.evolutio.presentation.shared

import androidx.transition.Transition


/**
 * For some reason TransitionListenerAdapter from the Android API has it's
 * minimum API requirment set to 26, but it's just a simple wrapper around
 * Transition.TransitionListener ( just as this class ), we decided to literally
 * copy the implementation and avoid the minimum sdk error
 */
abstract class TransitionAdapter : Transition.TransitionListener {
    override fun onTransitionStart(transition: Transition) {}
    override fun onTransitionEnd(transition: Transition) {}
    override fun onTransitionCancel(transition: Transition) {}
    override fun onTransitionPause(transition: Transition) {}
    override fun onTransitionResume(transition: Transition) {}
}

