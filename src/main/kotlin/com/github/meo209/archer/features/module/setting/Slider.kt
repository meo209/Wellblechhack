package com.github.meo209.archer.features.module.setting

import com.github.meo209.archer.features.module.Include

data class Slider<T: Comparable<T>>(@Include var value: T, val range: ClosedRange<T>)