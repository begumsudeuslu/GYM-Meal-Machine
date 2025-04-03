public class Product {
    private String name;
    private float pro;
    private float carb;
    private float fat;
    private int cal;
    private int price;
    private int count;

    public Product(String name, float pro, float carb, float fat, int price)  {
        this.name = name;
        this.pro = pro;
        this.carb = carb;
        this.fat = fat;
        this.cal = calculateCal(pro, carb, fat);
        this.price = price;
        this.count = 1;
    }

    public String getName()  {
        return name;
    }
 
    public void setName(String name)  {
        this.name = name;
    }

    public float getPro()  {
        return pro;
    }
     
    public void setPro(float pro)  {
        this.pro = pro;
    }

    public float getCarb()  {
        return carb;
    }
    
    public void setCarb(float carb)  {
        this.carb = carb;
    }

    public float getFat()  {
        return fat;
    }
    
    public void setFat(float fat)  {
        this.fat = fat;
    }

    public int getCal()  {
        return cal;
    }
    
    public void setCal(int cal)  {
        this.cal = cal;
    }

    public int getCount()  {
        return count;
    }
    
    public void setCount(int count)  {
        this.count = count;
    }

    public int getPrice()  {
        return price;
    }
    
    public void setPrice(int price)  {
        this.price = price;
    }

    private static int calculateCal(float pro, float carb, float fat) {
        float floatCal = (4*pro + 4*carb + 9*fat);
        return  Math.round(floatCal); 
    }
}