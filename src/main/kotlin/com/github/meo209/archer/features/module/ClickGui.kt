package com.github.meo209.archer.features.module

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
annotation class ClickGui


/**
 * Annotation to specify available options for a property.
 */
@Target(AnnotationTarget.CLASS)
annotation class Option(vararg val value: KClass<*>)


@Option(
    Implementation::class,
    Implementation2::class
)
interface BasicOption {
    
    fun something()
    
}

class Implementation: BasicOption {
    override fun something() {
        println("1")
    }
}

class Implementation2: BasicOption {
    override fun something() {
        println("2")
    }
}