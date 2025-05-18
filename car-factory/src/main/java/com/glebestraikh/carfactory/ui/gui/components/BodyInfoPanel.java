package com.glebestraikh.carfactory.ui.gui.components;

import com.glebestraikh.carfactory.ui.UIController;

public class BodyInfoPanel extends InfoPanel {

    public BodyInfoPanel(UIController controller) {
        super(controller);
        controller.addBodyStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Body produced: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Bodies in storage: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Body production time in sec: ";
    }

    @Override
    protected int getStorageCapacity() {
        return controller.getBodyStorageCapacity();
    }

    @Override
    protected void setFactoryTime(int time) {
        controller.setBodyProductionTime(time);
    }
}
