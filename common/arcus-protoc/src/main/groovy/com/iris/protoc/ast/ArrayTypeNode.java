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
package com.iris.protoc.ast;

public abstract class ArrayTypeNode extends TypeNode {
   protected final TypeNode valueType;

   public ArrayTypeNode(TypeNode valueType) {
      this.valueType = valueType;
   }

   public TypeNode getValueType(Aliasing resolver) {
      return resolver.resolve(valueType);
   }

   @Override
   public boolean isPrimitive() {
      return false;
   }

   public abstract String getLength(Aliasing resolver);
}

