package me.uwutaku.hexthingy

import at.petrak.hexcasting.api.casting.*
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadEntity
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

class AllayCreation : SpellAction {
    override val argc = 1

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val target = args.getLivingEntityButNotArmorStand(0)
        if(!(target.type == EntityType.VEX)) {
            throw MishapBadEntity(target, Component.literal("Vex"))
        }

        env.assertEntityInRange(target)
        val cost = MediaConstants.CRYSTAL_UNIT * 15
        return SpellAction.Result(
            Spell(target),
            cost,
            listOf(ParticleSpray.cloud(target.position().add(0.0, target.eyeHeight / 2.0, 0.0), 1.0))
        )
    }

    private class Spell(val entity: LivingEntity) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val serverWorld = entity.level() as? ServerLevel ?: return
            val pos = entity.blockPosition()
            entity.remove(Entity.RemovalReason.DISCARDED)
            val allay = Allay(EntityType.ALLAY, serverWorld)
            allay.setPos(pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5)
            serverWorld.addFreshEntity(allay)
        }
    }
}