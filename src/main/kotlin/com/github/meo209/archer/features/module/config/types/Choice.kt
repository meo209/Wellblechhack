package com.github.meo209.archer.features.module.config.types

import com.github.meo209.archer.features.module.Configurable

/**
 * Represents a collection of the currently selected choice and all other options
 */
class Choice(
    val options: List<NamedChoice> = emptyList(), // Default to an empty list
    var current: NamedChoice = options.firstOrNull() ?: DefaultNamedChoice() // Default to the first option or a default instance
) {

}

/**
 * A default implementation of [NamedChoice] for use in the no-argument constructor
 */
private class DefaultNamedChoice : NamedChoice("default")

/**
 * An entry in [Choice]
 * @see Configurable
 */
abstract class NamedChoice(name: String) : Configurable(name)