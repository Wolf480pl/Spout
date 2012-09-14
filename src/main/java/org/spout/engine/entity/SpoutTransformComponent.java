package org.spout.engine.entity;

import org.spout.api.component.components.EntityComponent;
import org.spout.api.component.components.TransformComponent;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.MathHelper;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.engine.util.thread.snapshotable.Snapshotable;
import org.spout.engine.util.thread.snapshotable.SnapshotableReference;


public class SpoutTransformComponent extends EntityComponent implements TransformComponent, Snapshotable {
	private final SnapshotableReference<Transform> transform = new SnapshotableReference<Transform>(new Transform());

	@Override
	public boolean isDetachable() {
		return false;
	}

	@Override
	public void onTick(float dt) {
	}

	@Override
	public void setTransform(Transform transform) {
		this.transform.getLive().set(transform);
	}

	@Override
	public Transform getTransform() {
		return transform.get().copy();
	}

	@Override
	public Transform getTransformLive() {
		return transform.getLive().copy();
	}

	@Override
	public boolean isDirty() {
		return transform.isDirty();
	}

	@Override
	public Point getPosition() {
		return transform.get().getPosition();
	}

	@Override
	public void setPosition(Point position) {
		if (position == null) {
			getHolder().remove();
			return;
		}
		transform.getLive().setPosition(position);
	}

	@Override
	public Quaternion getRotation() {
		return transform.get().getRotation();
	}

	@Override
	public void setRotation(Quaternion rotation) {
		if (rotation == null) {
			getHolder().remove();
			return;
		}
		transform.getLive().setRotation(rotation);
	}

	@Override
	public Vector3 getScale() {
		return transform.get().getScale();
	}

	@Override
	public void setScale(Vector3 scale) {
		if (scale == null) {
			getHolder().remove();
			return;
		}
		transform.getLive().setScale(scale);
	}

	/**
	 * Moves the entity by the provided vector<br/>
	 * @param amount to move the entity
	 */
	@Override
	public void translate(Vector3 amount) {
		transform.getLive().setPosition(transform.getLive().getPosition().add(amount));
	}

	/**
	 * Moves the entity by the provided vector
	 * @param x offset
	 * @param y offset
	 * @param z offset
	 */
	@Override
	public void translate(float x, float y, float z) {
		translate(new Vector3(x, y, z));
	}

	/**
	 * Rotates the entity about the provided axis by the provided angle
	 * @param ang
	 * @param x
	 * @param y
	 * @param z
	 */
	@Override
	public void rotate(float w, float x, float y, float z) {
		transform.getLive().setRotation(transform.getLive().getRotation().rotate(w, x, y, z));
	}

	/**
	 * Rotates the entity by the provided rotation
	 * @param rot
	 */
	@Override
	public void rotate(Quaternion rot) {
		transform.getLive().setRotation(transform.getLive().getRotation().multiply(rot));
	}

	/**
	 * Scales the entity by the provided amount
	 * @param amount
	 */
	@Override
	public void scale(Vector3 amount) {
		transform.getLive().setScale(transform.getLive().getScale().multiply(amount));
	}

	/**
	 * Scales the entity by the provided amount
	 * @param x
	 * @param y
	 * @param z
	 */
	@Override
	public void scale(float x, float y, float z) {
		scale(new Vector3(x, y, z));
	}

	/**
	 * Pitches the entity by the provided amount
	 * @param angle
	 */
	@Override
	public void pitch(float angle) {
		setPitch(angle);
	}

	/**
	 * Yaws the entity by the provided amount
	 * @param angle
	 */
	@Override
	public void yaw(float angle) {
		setYaw(angle);
	}

	/**
	 * Rolls the entity by the provided amount
	 * @param angle
	 */
	@Override
	public void roll(float angle) {
		setRoll(angle);
	}

	/**
	 * Gets the entities current pitch, or vertical angle.
	 * @return pitch of the entity
	 */
	@Override
	public float getPitch() {
		return getRotation().getPitch();
	}

	/**
	 * Gets the entities current yaw, or horizontal angle.
	 * @return yaw of the entity.
	 */
	@Override
	public float getYaw() {
		return getRotation().getYaw();
	}

	/**
	 * Gets the entities current roll as a float.
	 * @return roll of the entity
	 */
	@Override
	public float getRoll() {
		return getRotation().getRoll();
	}

	/**
	 * Sets the pitch of the entity.
	 * @param ang
	 */
	@Override
	public void setPitch(float angle) {
		setAxisAngles(getPitch(), getYaw(), angle);
	}

	/**
	 * Sets the roll of the entity.
	 * @param ang
	 */
	@Override
	public void setRoll(float angle) {
		setAxisAngles(getPitch(), getYaw(), angle);
	}

	/**
	 * sets the yaw of the entity.
	 * @param ang
	 */
	@Override
	public void setYaw(float angle) {
		setAxisAngles(getPitch(), angle, getRoll());
	}

	private void setAxisAngles(float pitch, float yaw, float roll) {
		setRotation(MathHelper.rotation(pitch, yaw, roll));
	}

	@Override
	public void copySnapshot() {
		transform.get().set(transform.getLive());
	}
}
