package org.spout.engine.entity.component;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import org.spout.api.component.components.PhysicsComponent;
import org.spout.api.entity.Entity;
import org.spout.api.geo.discrete.Point;
import org.spout.api.math.MathHelper;
import org.spout.api.math.Vector3;

/**
 * Implementation of PhysicsComponent that provides linear/angular velocity as well as snapshot values
 * of them.
 */
public class SpoutPhysicsComponent extends PhysicsComponent {
	private MotionState state;

	private Vector3 angularVelocity;
	private Vector3 angularVelocityLive;
	private Vector3 linearVelocity;
	private Vector3 linearVelocityLive;

	@Override
	public void onAttached() {
		state = new SpoutDefaultMotionState(getHolder());
	}

	@Override
	public Vector3 getAngularVelocity() {
		return angularVelocity;
	}

	@Override
	public Vector3 getAngularVelocityLive() {
		return angularVelocityLive;
	}

	@Override
	public Vector3 getLinearVelocity() {
		return linearVelocity;
	}

	@Override
	public Vector3 getLinearVelocityLive() {
		return linearVelocityLive;
	}

	@Override
	public void setAngularVelocity(Vector3 velocity) {
		angularVelocityLive = velocity;
	}

	@Override
	public void setLinearVelocity(Vector3 velocity) {
		linearVelocityLive = velocity;
	}

	@Override
	public void setVelocity(Vector3 velocity) {
		setAngularVelocity(velocity);
		setLinearVelocity(velocity);
	}

	@Override
	public MotionState getMotionState() {
		return state;
	}

	@Override
	public void setMotionState(MotionState state) {
		this.state = state;
	}

	@Override
	public boolean isVelocityDirty() {
		return !angularVelocity.equals(angularVelocityLive) && !linearVelocity.equals(linearVelocityLive);
	}

	public void copySnapshot() {
		angularVelocity = angularVelocityLive;
		linearVelocity = linearVelocityLive;
	}

	public void updateCollisionVelocity() {
		getCollisionObject().setInterpolationAngularVelocity(MathHelper.toVector3f(angularVelocityLive));
		getCollisionObject().setInterpolationLinearVelocity(MathHelper.toVector3f(linearVelocityLive));
	}

	//TODO Thread safety!! I think
	private static class SpoutDefaultMotionState extends DefaultMotionState {
		private final Entity entity;

		public SpoutDefaultMotionState(Entity entity) {
			this.entity = entity;
		}

		@Override
		public Transform getWorldTransform(Transform transform) {
			org.spout.api.geo.discrete.Transform spoutTransform = entity.getTransform().getTransform();
			transform.set(new Matrix4f(MathHelper.toQuaternionf(spoutTransform.getRotation()), MathHelper.toVector3f(spoutTransform.getPosition()), 1));
			return transform;
		}

		@Override
		public void setWorldTransform(Transform transform) {
			org.spout.api.geo.discrete.Transform spoutTransform = entity.getTransform().getTransformLive();
			spoutTransform.setPosition(new Point(MathHelper.toVector3(transform.origin), entity.getWorld()));
			spoutTransform.setRotation(MathHelper.toQuaternion(transform.getRotation(new Quat4f())));
		}
	}
}
