/*
 * This file is part of SpoutAPI.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutAPI is licensed under the SpoutDev License Version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.api.util.config.annotated;

import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.Configuration;
import org.spout.api.util.config.ConfigurationNodeSource;
import org.spout.api.util.config.ConfigurationWrapper;

public abstract class AnnotatedConfiguration extends ConfigurationWrapper {
	public AnnotatedConfiguration() {
	}

	public AnnotatedConfiguration(Configuration config) {
		super(config);
	}

	public abstract void load(ConfigurationNodeSource source) throws ConfigurationException;

	public abstract void save(ConfigurationNodeSource source) throws ConfigurationException;

	@Override
	public void load() throws ConfigurationException {
		super.load();
		load(this);
	}

	@Override
	public void save() throws ConfigurationException {
		save(this);
		super.save();
	}
}
