public class RegularPricing implements PricingStrategy
{
  @Override
  public double calculatePrice(HotelRoom room, long days) {
      return room.price * days;
    }
}
