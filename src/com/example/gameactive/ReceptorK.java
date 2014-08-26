/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gameactive;

/*
 * Receptor contain list receptor coefficients
 * @author Denis Terehin
 */
public class ReceptorK {
	private int mId;
	private float mK[];
	
	public ReceptorK(int id, float k[])
	{
		this.mId=id;
		this.mK=k;
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public float[] getK() {
		return mK;
	}

	public void setK(float mK[]) {
		this.mK = mK;
	}
	
}
