package com.github.meo209.archer.ui

import imgui.ImGuiIO

@FunctionalInterface
interface RenderInterface {
    
    fun render(io: ImGuiIO)
    
}