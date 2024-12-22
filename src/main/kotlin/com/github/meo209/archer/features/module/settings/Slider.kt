package com.github.meo209.archer.features.module.settings

import com.fasterxml.jackson.annotation.JsonIgnore

data class Slider(var value: Float, @JsonIgnore val min: Float = 0f, @JsonIgnore val max: Float = 1f)