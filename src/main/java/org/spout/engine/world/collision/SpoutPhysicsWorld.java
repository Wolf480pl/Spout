package org.spout.engine.world.collision;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.voxel.CollisionSnapshot;
import com.bulletphysics.collision.shapes.voxel.VoxelPhysicsWorld;

import org.spout.api.material.BlockMaterial;
import org.spout.api.math.MathHelper;
import org.spout.api.math.Vector3;

import org.spout.engine.world.SpoutBlock;
import org.spout.engine.world.SpoutRegion;

public class SpoutPhysicsWorld implements VoxelPhysicsWorld {
	private final SpoutRegion simulation;

	public SpoutPhysicsWorld(SpoutRegion simulation) {
		if (simulation == null) {
			throw new IllegalArgumentException("Region cannot be null for physics!");
		}
		this.simulation = simulation;
	}
	@Override
	public CollisionSnapshot getCollisionShapeAt(int x, int y, int z) {
		final SpoutBlock block = (SpoutBlock) simulation.getBlock(x, y, z, null);
		return new SpoutCollisionSnapshot(block.getMaterial(), block.getPosition());
	}

	private static class SpoutCollisionSnapshot implements CollisionSnapshot {
		private final boolean isColliding, isBlocking;
		private final CollisionShape shape;
		private final Vector3 position;

		public SpoutCollisionSnapshot(BlockMaterial material, Vector3 position) {
			this.shape = material.getCollisionShape();
			this.isColliding = shape != null && material.isInteractable();
			this.isBlocking = shape != null && !material.isPenetrable();
			this.position = position;
		}

		@Override
		public boolean isColliding() {
			return isColliding;
		}

		@Override
		public Object getUserData() {
			return position;
		}

		@Override
		public CollisionShape getCollisionShape() {
			return shape;
		}

		@Override
		public Vector3f getCollisionOffset() {
			return MathHelper.toVector3f(Vector3.ZERO); //TODO figure this out...
		}

		@Override
		public boolean isBlocking() {
			return isBlocking;
		}
	}
}
