package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.blocks.engines.Compressor;
import com.mmodding.innovation_insights.blocks.engines.Extractor;
import com.mmodding.innovation_insights.blocks.engines.FurnaceAssembler;
import com.mmodding.innovation_insights.blocks.reactors.ThermalReactorInterface;
import com.mmodding.innovation_insights.blocks.generators.AnvilFissionGenerator;
import com.mmodding.mmodding_lib.library.blocks.CustomBlock;
import com.mmodding.mmodding_lib.library.blocks.CustomTransparentBlock;
import com.mmodding.mmodding_lib.library.blocks.settings.DefaultBlockSettings;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class IIBlocks implements ElementsInitializer {

	public static final Compressor COMPRESSOR = new Compressor(
		QuiltBlockSettings.of(Material.STONE).hardness(3.0f).requiresTool(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_ENGINES
	);

	public static final CustomBlock CONDENSED_OBSIDIAN = new CustomBlock(
		QuiltBlockSettings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(100.0F, 2400.0F),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_MATERIALS
	);

	public static final CustomBlock STEEL_BLOCK = new CustomBlock(
		QuiltBlockSettings.of(Material.METAL).hardness(5.0f).requiresTool(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_MATERIALS
	);

	public static final AnvilFissionGenerator ANVIL_FISSION_GENERATOR = new AnvilFissionGenerator(
		QuiltBlockSettings.of(Material.METAL).hardness(5.0f).requiresTool(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_GENERATORS
	);

	public static final Extractor EXTRACTOR = new Extractor(
		QuiltBlockSettings.of(Material.METAL).hardness(5.0f).requiresTool(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_ENGINES
	);

	public static final CustomBlock BAUXITE_ORE = new CustomBlock(
		QuiltBlockSettings.of(Material.STONE).hardness(3.0f).requiresTool(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_ORES
	);

	public static final CustomBlock DEEPSLATE_BAUXITE_ORE = new CustomBlock(
		QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).hardness(4.5f),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_ORES
	);

	public static final CustomBlock BAUXITE_BLOCK = new CustomBlock(
		DefaultBlockSettings.ofDefault(DefaultBlockSettings.METAL_SETTINGS),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_MATERIALS
	);

	public static final FurnaceAssembler FURNACE_ASSEMBLER = new FurnaceAssembler(
		QuiltBlockSettings.of(Material.METAL),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_ENGINES
	);

	public static final CustomBlock ALUMINIUM_BLOCK = new CustomBlock(
		DefaultBlockSettings.ofDefault(DefaultBlockSettings.METAL_SETTINGS),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_MATERIALS
	);

	public static final CustomTransparentBlock THERMAL_GLASS = new CustomTransparentBlock(
		QuiltBlockSettings.of(Material.GLASS).hardness(0.5f).sounds(BlockSoundGroup.GLASS).nonOpaque(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_MATERIALS
	);

	public static final ThermalReactorInterface THERMAL_REACTOR_INTERFACE = new ThermalReactorInterface(
		QuiltBlockSettings.of(Material.METAL).hardness(5.0f).requiresTool(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_REACTORS
	);

	public static final CustomBlock THERMAL_REACTOR_CORE = new CustomBlock(
		QuiltBlockSettings.of(Material.METAL).hardness(5.0f).requiresTool(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_REACTORS
	);

	public static final CustomTransparentBlock THERMAL_REACTOR_CONTAINER = new CustomTransparentBlock(
		QuiltBlockSettings.of(Material.METAL).hardness(5.0f).requiresTool().nonOpaque(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_REACTORS
	);

	public static final CustomBlock THERMAL_REACTOR_FRAME = new CustomBlock(
		QuiltBlockSettings.of(Material.METAL).hardness(5.0f).requiresTool(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_REACTORS
	);

	@Override
	public void register() {
		COMPRESSOR.register(InnovationInsights.createId("compressor"));
		CONDENSED_OBSIDIAN.register(InnovationInsights.createId("condensed_obsidian"));
		STEEL_BLOCK.register(InnovationInsights.createId("steel_block"));
		ANVIL_FISSION_GENERATOR.register(InnovationInsights.createId("anvil_fission_generator"));
		EXTRACTOR.register(InnovationInsights.createId("extractor"));
		BAUXITE_ORE.register(InnovationInsights.createId("bauxite_ore"));
		DEEPSLATE_BAUXITE_ORE.register(InnovationInsights.createId("deepslate_bauxite_ore"));
		BAUXITE_BLOCK.register(InnovationInsights.createId("bauxite_block"));
		FURNACE_ASSEMBLER.register(InnovationInsights.createId("furnace_assembler"));
		ALUMINIUM_BLOCK.register(InnovationInsights.createId("aluminium_block"));
		THERMAL_GLASS.register(InnovationInsights.createId("thermal_glass"));
		THERMAL_REACTOR_INTERFACE.register(InnovationInsights.createId("thermal_reactor_interface"));
		THERMAL_REACTOR_CORE.register(InnovationInsights.createId("thermal_reactor_core"));
		THERMAL_REACTOR_CONTAINER.register(InnovationInsights.createId("thermal_reactor_container"));
		THERMAL_REACTOR_FRAME.register(InnovationInsights.createId("thermal_reactor_frame"));
	}
}
