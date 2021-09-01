package edu.unl.cse.csce361.car_rental.backend;

public class FeesInsurance extends PriceDecorator {

    public FeesInsurance(PricedItem pricedItem) {
        super(pricedItem);
    }

    @Override
    public int getDailyRate() {
        return 100;
    }

    @Override
    public String getLineItemSummary() {
        String description = toString();
        String dailyRate = CURRENCY_SYMBOL + " " + getDailyRate();
        int descriptionLength = description.length();
        int dailyRateLength = dailyRate.length();
        int numberOfLines = 1;
        while (descriptionLength > LINE_ITEM_TEXT_LENGTH - dailyRateLength - 1) {
            // place newline at index 80 on 1st iteration, 161 on 2nd (allows for previous newline), 242 on 3rd, etc.
            int index = numberOfLines * LINE_ITEM_TEXT_LENGTH + numberOfLines - 1;
            description = description.substring(0, index+1) + System.lineSeparator() + description.substring(index+1);
            descriptionLength -= LINE_ITEM_TEXT_LENGTH;
        }
        String padding = " ".repeat(LINE_ITEM_TEXT_LENGTH - descriptionLength - dailyRateLength);
        return description + padding + dailyRate;
    }

    @Override
    public PricedItem getBasePricedItem() {
        return this.getBasePricedItem();
    }
}
