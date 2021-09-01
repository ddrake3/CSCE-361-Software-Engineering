package edu.unl.cse.csce361.car_rental.backend;

public abstract class PriceDecorator implements PricedItem {

    protected PricedItem pricedItem;

    public PriceDecorator(PricedItem pricedItem) {
        this.pricedItem = pricedItem;
    }
    @Override
    public abstract int getDailyRate();

    @Override
    public abstract String getLineItemSummary();

    @Override
    public abstract PricedItem getBasePricedItem();

}
