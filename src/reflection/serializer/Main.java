package reflection.serializer;

public class Main {

    public static void main(String[] args) {

        Post post = new Post("Post with Count", "Text for post with count", 15);
        System.out.println("Original Post: " + post);

        String postAsString = new Serializer().serialize(post);
        System.out.println("Serialized Post: " + postAsString);

        Post restoredPost = new Serializer().deserialize(postAsString, Post.class);
        System.out.println("Deserialized Post: " + restoredPost);
        System.out.println("Deserialized Post Title: " + restoredPost.getTitle());
        System.out.println("Deserialized Post Text: " + restoredPost.getText());
        System.out.println("Deserialized Post Reply Count: " + restoredPost.getReplyCount());

        System.out.println("---");

        Customer customer = new Customer("Alice", "Smith");
        System.out.println("Original Customer: " + customer);

        String customerAsString = new Serializer().serialize(customer);
        System.out.println("Serialized Customer: " + customerAsString);

        Customer restoredCustomer = new Serializer().deserialize(customerAsString, Customer.class);
        System.out.println("Deserialized Customer: " + restoredCustomer);
        System.out.println("Deserialized Customer First Name: " + restoredCustomer.getFirstName());
        System.out.println("Deserialized Customer Last Name: " + restoredCustomer.getLastName());

        System.out.println("---");

        Customer customerWithSpecialChars = new Customer("Alice% and Bob:", "Smith|Doe");
        System.out.println("Original Customer with Special Chars: " + customerWithSpecialChars);

        String specialCharCustomerAsString = new Serializer().serialize(customerWithSpecialChars);
        System.out.println("Serialized Customer with Special Chars: " + specialCharCustomerAsString);

        Customer restoredSpecialCharCustomer = new Serializer().deserialize(specialCharCustomerAsString, Customer.class);
        System.out.println("Deserialized Customer with Special Chars: " + restoredSpecialCharCustomer);
        System.out.println("Deserialized Customer with Special Chars First Name: " + restoredSpecialCharCustomer.getFirstName());
        System.out.println("Deserialized Customer with Special Chars Last Name: " + restoredSpecialCharCustomer.getLastName());
    }
}
