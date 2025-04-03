import java.util.ArrayList;
import java.util.List;


// In this class, there are main and also helper methods that fill and retrieve the products.
public class Plug {

    // The array list run like the gym machine.
    private static List<Product> productList;


    /**
    * Precondition: Take two input list as parameters. Plug the product one by one with first input string list. Buy the product one by one with second input string list.
    * Postcondition: Finally this method will return all changes and movements in string list.
    * @param mixProduct it is taken from user and has information about product.
    * @param purchase it is taken from user and has information about what whanted.
    * @return return product information with changes, movements or whatever is output in string array.
    */
    public static List<String> machine(String[] mixProduct, String[] purchase) {
        
        productList = new ArrayList<>();     // create an array
        List<String> allOutput = new ArrayList<>();    //create string array
        
        // Take line and seperate the product information one by one. Then save this information in array list.
        for (String product : mixProduct) {
            String[] productInfo = product.split("\t");
            String name = productInfo[0];
            float pro = Float.parseFloat(productInfo[2].split(" ")[0]);        // Protein, carbohydrate, fat is must be float because double type is use more memory space.
            float carb = Float.parseFloat(productInfo[2].split(" ")[1]);
            float fat = Float.parseFloat(productInfo[2].split(" ")[2]);
            int price = Integer.parseInt(productInfo[1]);       // Price will be integer.
             
            // Call findProduct method. This method will be return product if find in array list or return null if cannot find array list.
            Product existingProduct = findProduct(name);           
        
            // If null other say there is no product type and also gym slot is less than 24 create new slot. 
            if (existingProduct == null  && productList.size() < 24 ) {
                productList.add(new Product(name, pro, carb, fat, price));
                
            // If there is product and product count less than 10, add product so add one to count in gym slot.
            }else if (existingProduct != null && existingProduct.getCount() < 10) {  
                existingProduct.setCount(existingProduct.getCount() + 1);
            
            // If none of the above conditions are met.
            }else {

                // If machine is not full yet
                if (fill()!= -1) {
                    String tooMuchProd = "INFO: There is no available place to put " + name + "\n";
                    allOutput.add(tooMuchProd);
                
                // If machine is full write output information.
                } else {
                    String ouputTryPro = "INFO: There is no available place to put " + name + "\n";
                    String ouputFull = "INFO: The machine is full!\n";
                    allOutput.add(ouputTryPro);
                    allOutput.add(ouputFull);
                    break;
                }
            }      
        } 

        allOutput.addAll(convertToStringList(productList));

        // Take line whatever product information which you want line by line and write movements and changes. 
        for (String line : purchase) {
            String[] infoLine = line.split("\t");
            int value = Integer.parseInt(infoLine[3]);
            String choice = infoLine[2];
            int totalMoney = 0;
            String[] cashMulti = infoLine[1].split(" ");

            // This line shows product information.
            String outputInput = "\nINPUT: CASH" + "\t" + String.join(" ", cashMulti) + "\t" + choice + "\t" + value  ;
            allOutput.add(outputInput);
            
            int[] acceptMoney = {1, 5, 10, 20, 50, 100, 200};
            boolean validCash = true; 
            for (String cash : cashMulti) { 
                boolean isValid = false;                        
                for (int accepted : acceptMoney) {       
                    // If money type is correct            
                    if (Integer.parseInt(cash) == accepted) {
                        totalMoney += Integer.parseInt(cash);
                        isValid = true;
                        break;
                    } 
                }
                // If money type is not correct, pass next money.
                if(!isValid)  {
                    validCash=false;
                    continue;
                }
            }

            // If correct money type is/ are in money list, write info line.
            if (!validCash) {
                String outputMoneyType = "\nINFO: There is a type of money that is not accepted.";
                allOutput.add(outputMoneyType);
            }

            // This is about choice type. If choice is number
            if (choice.equals("NUMBER")) {

                // Check if value is within the valid range
                if (value >= 0 && value < productList.size()) { 
                    Product product = productList.get(value);

                    // If index and slot is not empty, get product.
                    if (product.getCount() > 0) {
                        String outputGetNumber = "\nPURCHASE: You have bought one " + product.getName() + "\nRETURN: Returning your change: " + (totalMoney - product.getPrice()) + " TL";
                        product.setCount(product.getCount()-1);  // Product is taken so, count must be lower.
                        allOutput.add(outputGetNumber);
                    
                    // If slot of the number is empty, write info line about it.
                    } else {
                        String outputEmpty = "\nINFO: This slot is empty, your money will be returned.\nRETURN: Returning your change: " + totalMoney + " TL";
                        allOutput.add(outputEmpty);
                    }
                } 

                if(value < 24 && value > productList.size()) {
                // If value is out of bounds and also lower, write this info line.
                    String outputOutOfList = "\nINFO: This slot is empty, your money will be returned.\nRETURN: Returning your change: " + totalMoney + " TL";
                    allOutput.add(outputOutOfList);
                }
                // If value is out of bounds and also greater than 24, write this info line.
                if (value > 24)  {
                    String outputOutOfIndex = "\nINFO: Number cannot be accepted. Please try again with another number.\nRETURN: Returning your change: " + totalMoney + " TL";
                    allOutput.add(outputOutOfIndex);
                }

            } else {
            // Check if the product is suitable
                Product currentProduct = findAndChangeProduct(choice, value, totalMoney);

                // If the product is suitable but there isn't enough money, print "Insufficient money".
                if (currentProduct != null && currentProduct.getPrice() > totalMoney) {
                    String outputInsufMon = "\nINFO: Insufficient money, try again with more money.\nRETURN: Returning your change: " + totalMoney + " TL";
                    allOutput.add(outputInsufMon);
                }
    
                // If the product is not found, print "Product not founded".
                if (currentProduct == null) {
                    String outputNoProduct = "\nINFO: Product not found, your money will be returned.\nRETURN: Returning your change: " + totalMoney + " TL";
                    allOutput.add(outputNoProduct);
                }
    
                // If the product is suitable and there is enough money, proceed with the purchase.
                if (currentProduct != null && currentProduct.getPrice() <= totalMoney) {
                    currentProduct.setCount(currentProduct.getCount() - 1);  // Product is taken so, count must be lower.
                    String outputGetProd = "\nPURCHASE: You have bought one " + currentProduct.getName() + "\nRETURN: Returning your change: " + (totalMoney-currentProduct.getPrice()) + " TL";
                    allOutput.add(outputGetProd);
                }
            }
        }
        
        // Show last gym machine as a string.
        List<String> finalOutput = convertToStringList(productList);
        allOutput.add("\n");
        allOutput.addAll(finalOutput);

        return allOutput;  
    }
    
    /**
    * Precondition: Take three parameters which string type, integer value, int money.
    * Postcondition: Finally this method will return suitable product or method couldnt find any suitable product return null.
    * @param type type which whatever type: protein, carbohydrate, fat.
    * @param value value whatever or choose count of nutrients.
    * @param money money is your total accepted money.
    * @return return suitable product or null if you couldnt find.
    */
    private static Product findAndChangeProduct(String type, int value, int money) {
        for (Product product : productList) {
            switch (type) {
                case "PROTEIN":
                    if (Math.abs(product.getPro() - value) <= 5) {      
                        return product;
                    }
                    break;
  
                case "CARB":
                    if (Math.abs(product.getCarb() - value) <= 5) {
                        return product;
                    }
                    break;
 
                case "FAT":
                    if (Math.abs(product.getFat() - value) <= 5) {
                        return product;
                    }
                    break;
 
                case "CALORIE":
                    if (Math.abs(product.getCal() - value) <= 5) {
                        return product;
                    }
                    break;
            }
        }
        return null;
    }

    /**
    * Precondition: Take one parameters as productName.
    * Postcondition: Finally this method will return and check the product if suitable product slot is founded or return null if not founded.
    * @param productName is product name of slot which are looking for.
    * @return return product if slot is suitable or return null if you couldn't find.
    */
    private static Product findProduct(String productName) {
        for (Product product : productList) {
            if (product.getCount() < 10)  {
                if (product.getName().equals(productName)) {
                    return product;
                }
            }
        }
        return null;
     }
    
    /**
     * Precondition: Take one parameters as array list.
     * Postcondition: Finally this method will return style of machine with slot and also product count and calorie too.
     * @param productList is array list which we put the product with information.
     * @return gym machine as wanted and show product information too as string line by line.
     */
    public static List<String> convertToStringList(List<Product> productList) {
        List<String> allOutput = new ArrayList<>();
        
        allOutput.add("-----Gym Meal Machine-----");
        int count = 0;
         
        for (Product product : productList) {    
            // In this machine has four product in each six line.
            if (count % 4 == 0) {
                allOutput.add("\n");
            }
            if (product.getCount() != 0) {
                allOutput.add(product.getName() + "(" + product.getCal() + ", " + product.getCount() + ")___");
            } else {
                allOutput.add("___(0,0)___");
            }
            count++;
        }

        //This machine has twenty four slot.
        while (count < 24) {
            if (count % 4 == 0) {
                allOutput.add("\n");
            }
            allOutput.add("___(0, 0____");
            count++;
        }
        allOutput.add("\n----------");
        return allOutput;
    }

    /**
     * Precondition: In this method any take parameters.
     * Postcondition: This method will be return 0 if machine is not full or return -1 if machine is full.
     * @return 0 if not full yet or -1 if machine is full.
     */
    private static int fill()   {
        for (Product product : productList )  {
            if (product.getCount() < 10)  {
                return 0;
            } 
        }
        return -1;
    }
}
