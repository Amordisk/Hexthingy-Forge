package me.uwutaku.hexthingy

import at.petrak.hexcasting.api.casting.*
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity

class CleanEffect : SpellAction {
    override val argc = 1

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val target = args.getLivingEntityButNotArmorStand(0)
        var cost = 0.0
        val effects = ArrayList<MobEffect>()
        
        for (effectInstance in target.activeEffects) {
            if (!effectInstance.isInfiniteDuration) {
                effects.add(effectInstance.effect)
                cost += effectInstance.duration * (effectInstance.amplifier + 1) * MediaConstants.DUST_UNIT
            }
        }
        
        return SpellAction.Result(
            Spell(target, effects),
            cost.toLong(),
            listOf(ParticleSpray.cloud(target.position().add(0.0, target.eyeHeight / 2.0, 0.0), 1.0))
        )
    }

    private class Spell(val target: LivingEntity, val effects: ArrayList<MobEffect>) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            for (effect in effects) {
                target.removeEffect(effect)
            }
        }
    }
}