package com.github.meo209.archer.ui

import com.github.meo209.archer.Archer
import com.github.meo209.archer.events.ClientShutdownEvent
import com.github.meo209.keventbus.EventBus
import com.github.meo209.keventbus.FunctionTarget
import imgui.ImGui
import imgui.ImGuiIO
import imgui.ImGuiStyle
import imgui.ImVec4
import imgui.extension.implot.ImPlot
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiConfigFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import net.minecraft.client.MinecraftClient

// Stolen from FlorianMichael
// https://github.com/FlorianMichael/fabric-imgui-example-mod/blob/1.21.4/src/main/java/de/florianmichael/imguiexample/imgui/ImGuiImpl.java
object ArcherImGui {
    
    private val glfw = ImGuiImplGlfw()
    private val gl3 = ImGuiImplGl3()
    
    init {
        ImGui.createContext()
        ImPlot.createContext()

        applyStyle()
        
        val data = ImGui.getIO()
        
        data.iniFilename = Archer.Data.MOD_ID
        data.fontGlobalScale = 1f
        
        data.configFlags = ImGuiConfigFlags.DockingEnable
        
        glfw.init(MinecraftClient.getInstance().window.handle, true)
        gl3.init()
        
        EventBus.global().function<ClientShutdownEvent>(::dispose)
    }
    
    private fun applyStyle(style: ImGuiStyle = ImGui.getStyle()) {
        with(style) {
            alpha = 1.0f
            disabledAlpha = 1.0f
            windowPadding.set(12.0f, 12.0f)
            windowRounding = 0.0f
            windowBorderSize = 0.0f
            windowMinSize.set(20.0f, 20.0f)
            windowTitleAlign.set(0.5f, 0.5f)
            childRounding = 0.0f
            childBorderSize = 1.0f
            popupRounding = 0.0f
            popupBorderSize = 1.0f
            framePadding.set(6.0f, 6.0f)
            frameRounding = 0.0f
            frameBorderSize = 0.0f
            itemSpacing.set(12.0f, 6.0f)
            itemInnerSpacing.set(6.0f, 3.0f)
            cellPadding.set(12.0f, 6.0f)
            indentSpacing = 20.0f
            columnsMinSpacing = 6.0f
            scrollbarSize = 12.0f
            scrollbarRounding = 0.0f
            grabMinSize = 12.0f
            grabRounding = 0.0f
            tabRounding = 0.0f
            tabBorderSize = 0.0f
            tabMinWidthForCloseButton = 0.0f
            buttonTextAlign.set(0.5f, 0.5f)
            selectableTextAlign.set(0.0f, 0.0f)
        }

        ImGui.pushStyleColor(ImGuiCol.Text, ImVec4(1f, 1f, 1f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TextDisabled, ImVec4(70f / 255f, 81f / 255f, 115f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.WindowBg, ImVec4(20f / 255f, 22f / 255f, 26f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ChildBg, ImVec4(20f / 255f, 22f / 255f, 26f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.PopupBg, ImVec4(20f / 255f, 22f / 255f, 26f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.Border, ImVec4(40f / 255f, 43f / 255f, 49f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.BorderShadow, ImVec4(20f / 255f, 22f / 255f, 26f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.FrameBg, ImVec4(30f / 255f, 34f / 255f, 38f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.FrameBgHovered, ImVec4(40f / 255f, 43f / 255f, 49f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.FrameBgActive, ImVec4(60f / 255f, 55f / 255f, 152f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TitleBg, ImVec4(12f / 255f, 14f / 255f, 18f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, ImVec4(12f / 255f, 14f / 255f, 18f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TitleBgCollapsed, ImVec4(20f / 255f, 22f / 255f, 26f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.MenuBarBg, ImVec4(25f / 255f, 27f / 255f, 31f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ScrollbarBg, ImVec4(12f / 255f, 14f / 255f, 18f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ScrollbarGrab, ImVec4(30f / 255f, 34f / 255f, 38f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ScrollbarGrabHovered, ImVec4(40f / 255f, 43f / 255f, 49f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ScrollbarGrabActive, ImVec4(30f / 255f, 34f / 255f, 38f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.CheckMark, ImVec4(127f / 255f, 131f / 255f, 255f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.SliderGrab, ImVec4(127f / 255f, 131f / 255f, 255f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.SliderGrabActive, ImVec4(137f / 255f, 141f / 255f, 255f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.Button, ImVec4(30f / 255f, 34f / 255f, 38f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImVec4(50f / 255f, 45f / 255f, 139f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImVec4(60f / 255f, 55f / 255f, 152f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.Header, ImVec4(30f / 255f, 34f / 255f, 38f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.HeaderHovered, ImVec4(50f / 255f, 45f / 255f, 139f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.HeaderActive, ImVec4(60f / 255f, 55f / 255f, 152f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.Separator, ImVec4(40f / 255f, 47f / 255f, 64f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.SeparatorHovered, ImVec4(40f / 255f, 47f / 255f, 64f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.SeparatorActive, ImVec4(40f / 255f, 47f / 255f, 64f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ResizeGrip, ImVec4(30f / 255f, 34f / 255f, 38f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ResizeGripHovered, ImVec4(50f / 255f, 45f / 255f, 139f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.ResizeGripActive, ImVec4(60f / 255f, 55f / 255f, 152f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.Tab, ImVec4(12f / 255f, 14f / 255f, 18f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TabHovered, ImVec4(30f / 255f, 34f / 255f, 38f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TabActive, ImVec4(25f / 255f, 27f / 255f, 31f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TabUnfocused, ImVec4(12f / 255f, 14f / 255f, 18f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TabUnfocusedActive, ImVec4(20f / 255f, 22f / 255f, 26f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.PlotLines, ImVec4(133f / 255f, 153f / 255f, 179f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.PlotLinesHovered, ImVec4(10f / 255f, 250f / 255f, 250f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.PlotHistogram, ImVec4(255f / 255f, 74f / 255f, 152f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.PlotHistogramHovered, ImVec4(254f / 255f, 121f / 255f, 178f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TableHeaderBg, ImVec4(12f / 255f, 14f / 255f, 18f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TableBorderStrong, ImVec4(12f / 255f, 14f / 255f, 18f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TableBorderLight, ImVec4(0f, 0f, 0f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TableRowBg, ImVec4(30f / 255f, 34f / 255f, 38f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TableRowBgAlt, ImVec4(25f / 255f, 27f / 255f, 31f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.TextSelectedBg, ImVec4(60f / 255f, 55f / 255f, 152f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.DragDropTarget, ImVec4(127f / 255f, 131f / 255f, 255f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.NavHighlight, ImVec4(127f / 255f, 131f / 255f, 255f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.NavWindowingHighlight, ImVec4(127f / 255f, 131f / 255f, 255f / 255f, 1f))
        ImGui.pushStyleColor(ImGuiCol.NavWindowingDimBg, ImVec4(50f / 255f, 45f / 255f, 139f / 255f, 0.5f))
        ImGui.pushStyleColor(ImGuiCol.ModalWindowDimBg, ImVec4(50f / 255f, 45f / 255f, 139f / 255f, 0.5f))
    }
    
    fun render(renderInterface: RenderInterface) {
        gl3.newFrame()
        glfw.newFrame()
        
        ImGui.newFrame()
        
        renderInterface.render(ImGui.getIO())
        
        ImGui.render()
        gl3.renderDrawData(ImGui.getDrawData())
    }
    
    fun render(block: (ImGuiIO) -> Unit) {
        render(object : RenderInterface {
            override fun render(io: ImGuiIO) {
                block(io)
            }
        })
    }
    
    @FunctionTarget
    fun dispose(event: ClientShutdownEvent) {
        gl3.shutdown()
        
        ImGui.destroyContext()
        ImPlot.destroyContext()
    }

    // Statically load ArcherImGui
    fun init() {}
    
}