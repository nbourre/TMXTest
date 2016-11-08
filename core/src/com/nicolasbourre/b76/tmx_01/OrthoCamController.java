/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
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
 ******************************************************************************/

package com.nicolasbourre.b76.tmx_01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
	final OrthographicCamera camera;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();

    public int upKey = Input.Keys.W;
    protected boolean upPressed;
    public int downKey = Input.Keys.S;
    protected boolean downPressed;
    public int leftKey = Input.Keys.A;
    protected boolean leftPressed;
    public int rightKey = Input.Keys.D;
    protected boolean rightPressed;

    /** Update the camera if value changed */
    public boolean autoUpdate = false;

    protected float xSpeed = 5f;
    protected float ySpeed = 5f;

	public OrthoCamController (OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		camera.unproject(curr.set(x, y, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			camera.unproject(delta.set(last.x, last.y, 0));
			delta.sub(curr);
			camera.position.add(delta.x, delta.y, 0);
		}
		last.set(x, y, 0);
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		last.set(-1, -1, -1);
		return false;
	}

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == upKey)
            upPressed = true;
        else if (keycode == leftKey)
            leftPressed = true;
        else if (keycode == downKey)
            downPressed = true;
        else if (keycode == rightKey)
            rightPressed = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == upKey)
            upPressed = false;
        else if (keycode == leftKey)
            leftPressed = false;
        else if (keycode == downKey)
            downPressed = false;
        else if (keycode == rightKey)
            rightPressed = false;
        return super.keyUp(keycode);
    }

    public void setTranslationSpeed (float xSpeed, float ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void update () {
        if (upPressed || downPressed || leftPressed || rightPressed) {
            final float delta = Gdx.graphics.getDeltaTime();

            if (upPressed) camera.translate(0, delta * ySpeed);
            if (downPressed) camera.translate(0, delta * -ySpeed);
            if (leftPressed) camera.translate(delta * -xSpeed, 0);
            if (rightPressed) camera.translate(delta * xSpeed, 0);

            if (autoUpdate) camera.update();
        }
    }
}
