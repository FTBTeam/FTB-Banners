package dev.ftb.mods.ftbbanners.banners.image;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

/**
 * @author LatvianModder
 */
public class ImageBannerBlock extends HorizontalDirectionalBlock implements EntityBlock {
	private static final Predicate<Entity> PREDICATE = entity -> entity instanceof Player && ((Player) entity).isCreative();

	public ImageBannerBlock() {
		super(Properties.of(Material.BARRIER).strength(6000000F, 6000000F).noCollission().noLootTable());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ImageBannerEntity(pos, state);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext e && e.getEntity() instanceof Player p && !p.isCreative()) {
			return Shapes.empty();
		}
		return super.getShape(state, world, pos, context);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, BlockGetter p_220071_2_, BlockPos p_220071_3_, CollisionContext p_220071_4_) {
		return Shapes.empty();
	}

	@Override
	public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
		return false;
	}

	@Override
	public RenderShape getRenderShape(BlockState p_149645_1_) {
		return RenderShape.INVISIBLE;
	}

}
