package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static String URL = "jdbc:mysql://localhost:3306/tienda";
    static String USER = "root";
    static String PASS = "root";
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    public static void main(String[] args) {
        menu();
        Scanner scanner= new Scanner(System.in);
        int opcion = scanner.nextInt();
        switch (opcion) {

            case 1 -> {
                insertInitialiceData();

            }
            case 2 -> insertarProducto(scanner);
            case 3->showProductpsInPriceRange(scanner);
case 4-> updateProduct(scanner);


        }
    }

    private static void menu() {
        System.out.println("""
                1.InsertInitialialData
                2.->insertProducto
                3.>UpdateProduct
                4.->showProductsInPriceRange
                """);
    }


    public static void insertarProducto(Scanner scanner) {
        String sql = "INSERT INTO productos(nombre, precio, stock, id_fabricante) VALUES (?, ?, ?, ?)";
        System.out.println("Indical el nombre del producto ");
        String nombre= scanner.nextLine();
        double precio;
        while (true) {
        System.out.println("Indica el precio minimo 000.00 maimo 9999.99");
           precio = Double.parseDouble(scanner.nextLine());
            if (precio<999.99&&precio>0) break;
        }
        System.out.println("Indica el stock del producto");
        int stock=Integer.parseInt(scanner.nextLine());
        System.out.println("Indica el id del fabricante");
        int id_fabricante= Integer.parseInt(scanner.nextLine());
        try (Connection connection = conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);
            ps.setInt(4, id_fabricante);

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showProductpsInPriceRange(Scanner sc) {
        try (Connection conexion = conectar()) {
            System.out.print("Ingrese el precio minimo ");
            String min = sc.nextLine();
            System.out.print("Ingrese el precio maximo ");
            String max = sc.nextLine();
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM productos WHERE precio BETWEEN ? AND ?");
            ps.setString(1, min );
            ResultSet rs = ps.executeQuery();
            boolean comprobante;
            while (rs.next()) {
                if (rs.getInt("id_producto")<0)comprobante=false;
                System.out.println(rs.getInt("id_producto") + ": " + rs.getString("nombre")+rs.getDouble("precio"));
            }
            if (false) System.out.println("No hay tabals que se puedan leer");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void updateProduct(Scanner sc) {
        try (Connection conexion = conectar()) {
            System.out.print("id del producto ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nombre del producto: ");
            String nombre = sc.nextLine();
            System.out.print("precio del producto: ");
            double precio=  Double.parseDouble(sc.nextLine());
            System.out.print("Nuevo precio: ");
            int stock = Integer.parseInt(sc.nextLine());
            PreparedStatement pstm=conexion.prepareStatement("Select from productos where id = ?");
            pstm.setInt(1,id);
            ResultSet rs=pstm.executeQuery();
            if (rs.getString("nombre").isEmpty()) System.out.println("no hay ningun producto con este id");
            else {
            PreparedStatement ps = conexion.prepareStatement("UPDATE productos SET nombre = ?, precio = ?,stock = ? WHERE id = ?");
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);
            ps.setInt(4,id);
            ps.executeUpdate();}
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
        public static void insertInitialiceData(){
        try {Connection connection= conectar();
            PreparedStatement pstmt=connection.prepareStatement("");
            String [] tienda=new String[5];
            tienda[0]="'INSERT INTO fabricante values(1,'ASUS', 'Taiwan');";
            tienda[1]="INSERT INTO fabricante values(2,'Lenovo', 'China');";
            tienda[2]="INSERT INTO fabricante values(3,'Hewlett-Packard', 'Estados Unidos');";
            tienda[3]="INSERT INTO fabricante values(4,'Samsung', 'Corea del Sur');";
            tienda[4]="INSERT INTO fabricante values(5,'Seaguette', 'Estados Unidos');";
            for (int i = 0; i < tienda.length ; i++) {
                pstmt.executeUpdate(tienda[i]);
            }
            tienda[0]="'INSERT INTO producto values(1,'Didsco duro SATA3 1TB',86.99, 25,5);";
            tienda[1]="'INSERT INTO producto values(2,'Memoria RAM 8GB',120.00, 15,2);";
            tienda[2]="INSERT INTO producto values(3,'Disco SSD  1TB',150.99, 10, 4); ";
            tienda[3]="INSERT INTO producto values(4,'Monitor 24 LED Full HD', 202.00,12,1);";
            tienda[4]="INSERT INTO producto values(5,'Portatil Yoga 520',559.00,8,2);";
            for (int i = 0; i < tienda.length ; i++) {
                pstmt.executeUpdate(tienda[i]);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}