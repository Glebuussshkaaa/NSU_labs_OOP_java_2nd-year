package com.glebestraikh.carfactory.ui.gui.components;

import com.glebestraikh.carfactory.ui.UIController;

public class CarInfoPanel extends InfoPanel {

    public CarInfoPanel(UIController controller) {
        super(controller);
        controller.addCarStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Car produced: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Cars in storage: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Car sale time in sec: ";
    }

    @Override
    protected int getStorageCapacity() {
        return controller.getCarStorageCapacity();
    }

    @Override
    protected void setFactoryTime(int time) {
        controller.setCarSaleTime(time);
    }
}
