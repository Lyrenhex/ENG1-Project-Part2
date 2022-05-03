package com.lyrenhex;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.HashMap;
import java.util.Map;

public class GdxTestsRunner extends BlockJUnit4ClassRunner implements ApplicationListener {
    private  final Map<FrameworkMethod, RunNotifier> instance = new HashMap<>();

    /**
     *
     * @param testClass the class this runner is testing.
     * @throws InitializationError if the class cannot be run.
     */
    public GdxTestsRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

        // run the tests as a headless Gdx application.
        new HeadlessApplication(this, new HeadlessApplicationConfiguration());
    }

    @Override
    public void create() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void render() {
        // run the tests (keys) to get results (values).
        for (Map.Entry<FrameworkMethod, RunNotifier> val : instance.entrySet()) {
            super.runChild(val.getKey(), val.getValue());
        }
        instance.clear();
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() { }

    /**
     * @param test the test to be run
     * @param result refers to whether it passes the tests or not
     */
    @Override
    protected void runChild(FrameworkMethod test, RunNotifier result) {
        synchronized(instance){
            instance.put(test, result);
        }

        //wait until that test is invoked and continues only if execution was successful
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}
