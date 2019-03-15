/*
 * Copyright 2019 Arcus Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iris.agent.util;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentCappedLinkedQueue<T> extends ConcurrentLinkedQueue<T> {
	private static final long serialVersionUID = 2109950768032929441L;
	private final int cap;

	public ConcurrentCappedLinkedQueue(int cap) {
		this.cap = cap > 0 ? cap : 1;
	}

	@Override
	public boolean add(T e) {
		boolean addResult = super.add(e);
		while (size() > cap) {
			poll();
		}
		return addResult;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean addResult = super.addAll(c);
		while (size() > cap) {
			poll();
		}
		return addResult;
	}
	
	
}

