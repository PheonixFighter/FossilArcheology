package mods.fossil;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class RenderHUD implements ITickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if(Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().currentScreen != null)
			return;
		if(!Fossil.FossilOptions.Skull_Overlay)
		{
			return;
		}
		ItemStack helmet = Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(3);
		if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && helmet != null && helmet.itemID == Fossil.skullHelmet.itemID)
		{
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
			
			Tessellator t = Tessellator.instance;
			
			ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
			int width = scale.getScaledWidth();
			int height = scale.getScaledHeight();
			
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("%blur%/misc/skullhelmetblur.png"));
			
			t.startDrawingQuads();
			t.addVertexWithUV(0.0D, (double)height, 90.0D, 0.0D, 1.0D);
			t.addVertexWithUV((double)width, (double)height, 90.0D, 1.0D, 1.0D);
			t.addVertexWithUV((double)width, 0.0D, 90.0D, 1.0D, 0.0D);
			t.addVertexWithUV(0.0D, 0.0D, 90.0D, 0.0D, 0.0D);
			t.draw();
			
			GL11.glPopAttrib();
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return "render hud tick handler";
	}

}
