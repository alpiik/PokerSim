package poly.customer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CustomerRepository {

    private static final String FILE_PATH = "src/poly/customer/data.txt";

    private List<AbstractCustomer> customers = new ArrayList<>();

    public CustomerRepository() {
        loadCustomers();
    }

    private void loadCustomers() {
        customers.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(";");
                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                int bonusPoints = Integer.parseInt(parts[3]);

                switch (type) {
                    case "REGULAR":
                        LocalDate lastOrderDate = LocalDate.parse(parts[4]);
                        customers.add(new RegularCustomer(id, name, bonusPoints, lastOrderDate));
                        break;
                    case "GOLD":
                        customers.add(new GoldCustomer(id, name, bonusPoints));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown customer type: " + type);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read customer data from file: " + FILE_PATH, e);
        }
    }

    private void saveCustomers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (AbstractCustomer customer : customers) {
                writer.write(customer.asString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write customer data to file: " + FILE_PATH, e);
        }
    }

    public Optional<AbstractCustomer> getCustomerById(String id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    public void remove(String id) {
        if (customers.removeIf(customer -> customer.getId().equals(id))) {
            saveCustomers();
        }
    }

    public void save(AbstractCustomer customer) {
        Optional<AbstractCustomer> existingCustomerOptional = getCustomerById(customer.getId());

        if (existingCustomerOptional.isPresent()) {
            AbstractCustomer existingCustomer = existingCustomerOptional.get();
            if (existingCustomer instanceof RegularCustomer regularExisting && customer instanceof RegularCustomer regularNew) {
                {
                    regularExisting.name = regularNew.name;
                    regularExisting.bonusPoints = regularNew.bonusPoints;
                    regularExisting.setLastOrderDate(regularNew.getLastOrderDate());
                }
            } else if (existingCustomer instanceof GoldCustomer goldExisting && customer instanceof GoldCustomer goldNew) {
                {
                    goldExisting.name = goldNew.name;
                    goldExisting.bonusPoints = goldNew.bonusPoints;
                }
            } else {
                {
                    customers.remove(existingCustomer);
                    customers.add(customer);
                }
            }
        } else {
            customers.add(customer);
        }
        saveCustomers();
    }

    public int getCustomerCount() {
        return customers.size();
    }

    public List<AbstractCustomer> getAllPaged(int pageNumber, int pageSize) {
        int start = pageNumber * pageSize;
        int end = Math.min(start + pageSize, customers.size());
        if (start >= customers.size()) {
            return List.of();
        }
        return new ArrayList<>(customers.subList(start, end));
    }
}