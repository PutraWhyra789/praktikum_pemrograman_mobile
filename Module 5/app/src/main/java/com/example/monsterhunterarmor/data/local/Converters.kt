package com.example.monsterhunterarmor.data.local

import androidx.room.TypeConverter
import com.example.monsterhunterarmor.data.model.ArmorSkill
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromSkillList(skills: List<ArmorSkill>): String {
        return Json.encodeToString(skills)
    }

    @TypeConverter
    fun toSkillList(skillsJson: String): List<ArmorSkill> {
        return Json.decodeFromString(skillsJson)
    }
}