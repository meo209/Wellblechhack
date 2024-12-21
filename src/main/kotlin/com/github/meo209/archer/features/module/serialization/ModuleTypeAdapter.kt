package com.github.meo209.archer.features.module.serialization

import com.github.meo209.archer.Archer
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.github.meo209.archer.features.module.Module
import java.lang.reflect.Type

class ModuleTypeAdapter : JsonDeserializer<Module?> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): Module? {
        val jsonObject: JsonObject = json.asJsonObject
        val typeName: String = jsonObject.get("type").asString

        try {
            val cls: Class<out Module?> = Class.forName(typeName) as Class<out Module?>
            return Archer.GSON.fromJson(json, cls)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e)
        }
    }

}
