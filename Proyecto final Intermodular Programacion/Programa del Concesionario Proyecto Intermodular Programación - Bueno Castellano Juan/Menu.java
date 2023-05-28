import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

   public class Menu {
    public static void main(String[] args) {
        try {
            // Establecer conexión con la base de datos
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionariotesla", "root", "");

            // Menú principal
            Scanner scanner = new Scanner(System.in);
            int opcion;
            do {
                System.out.println("=== MENÚ PRINCIPAL ===");
                System.out.println("1. Coches");
                System.out.println("2. Clientes");
                System.out.println("3. Compras");
                System.out.println("4. Revisiones");
                System.out.println("0. Salir");
                System.out.print("Ingrese una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada

                switch (opcion) {
                    case 1:
                        menuCoches(connection, scanner);
                        break;
                    case 2:
                        menuClientes(connection, scanner);
                        break;
                    case 3:
                        menuCompras(connection, scanner);
                        break;
                    case 4:
                        menuRevisiones(connection, scanner);
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
                System.out.println();
            } while (opcion != 0);

            // Cerrar la conexión con la base de datos
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    private static void menuCoches(Connection connection, Scanner scanner) throws SQLException {
        int opcion;
        do {
            System.out.println("=== MENÚ COCHES ===");
            System.out.println("1. Mostrar coches");
            System.out.println("2. Añadir coche");
            System.out.println("3. Editar coche");
            System.out.println("4. Borrar coche");
            System.out.println("0. Volver al menú principal");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    mostrarCoches(connection);
                    break;
                case 2:
                    agregarCoche(connection, scanner);
                    break;
                case 3:
                    editarCoche(connection, scanner);
                    break;
                case 4:
                    borrarCoche(connection, scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    private static void mostrarCoches(Connection connection) {
        Set<Coches> coches = Coches.mostrarCoches(connection);
        System.out.println("=== LISTA DE COCHES ===");
        for (Coches coche : coches) {
            System.out.println(coche);
        }
    }

    private static void agregarCoche(Connection connection, Scanner scanner) {
        Coches coche = new Coches();
        // Solicitar los datos del coche al usuario y asignarlos a las propiedades del objeto coche
        // ...
        try {
            coche.save(connection); // Guardar el nuevo coche en la base de datos
            System.out.println("El coche ha sido añadido exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al añadir el coche: " + e.getMessage());
        }
    }

    private static void editarCoche(Connection connection, Scanner scanner) throws SQLException {
        // Solicitar al usuario la matrícula del coche a editar
        System.out.print("Ingrese la matrícula del coche a editar: ");
        String matricula = scanner.nextLine();

        // Buscar el coche en la base de datos utilizando la matrícula
        Coches coche = Coches.buscarCochePorMatricula(connection, matricula);
        if (coche != null) {
            // Mostrar los datos actuales del coche
            System.out.println("Datos actuales del coche:");
            System.out.println(coche);

            // Solicitar los nuevos datos del coche al usuario y asignarlos a las propiedades del objeto coche
            // ...

            try {
                coche.update(connection); // Actualizar los datos del coche en la base de datos
                System.out.println("El coche ha sido actualizado exitosamente.");
            } catch (SQLException e) {
                System.out.println("Error al editar el coche: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró ningún coche con la matrícula proporcionada.");
        }
    }

    private static void borrarCoche(Connection connection, Scanner scanner) throws SQLException {
        // Solicitar al usuario la matrícula del coche a borrar
        System.out.print("Ingrese la matrícula del coche a borrar: ");
        String matricula = scanner.nextLine();

        // Buscar el coche en la base de datos utilizando la matrícula
        Coches coche = Coches.buscarCochePorMatricula(connection, matricula);
        if (coche != null) {
            // Mostrar los datos del coche antes de borrarlo
            System.out.println("Datos del coche a borrar:");
            System.out.println(coche);

            // Confirmar la acción de borrado al usuario
            System.out.print("¿Está seguro de que desea borrar este coche? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();
            if (confirmacion.equals("s")) {
                try {
                    coche.delete(connection); // Borrar el coche de la base de datos
                    System.out.println("El coche ha sido borrado exitosamente.");
                } catch (SQLException e) {
                    System.out.println("Error al borrar el coche: " + e.getMessage());
                }
            } else {
                System.out.println("Operación de borrado cancelada.");
            }
        } else {
            System.out.println("No se encontró ningún coche con la matrícula proporcionada.");
        }
    }

    // Implementa los métodos menuClientes, menuCompras y menuRevisiones de manera similar a menuCoches
    // para proporcionar opciones de mostrar, añadir, editar y borrar registros en las tablas correspondientes

    private static void menuClientes(Connection connection, Scanner scanner) throws SQLException {
        int opcion;
        do {
            System.out.println("=== MENÚ CLIENTES ===");
            System.out.println("1. Mostrar clientes");
            System.out.println("2. Añadir cliente");
            System.out.println("3. Editar cliente");
            System.out.println("4. Borrar cliente");
            System.out.println("0. Volver al menú principal");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    mostrarClientes(connection);
                    break;
                case 2:
                    agregarCliente(connection, scanner);
                    break;
                case 3:
                    editarCliente(connection, scanner);
                    break;
                case 4:
                    borrarCliente(connection, scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    private static void mostrarClientes(Connection connection) {
        Set<Clientes> clientes = Clientes.mostrarClientes(connection);
        System.out.println("=== LISTA DE CLIENTES ===");
        for (Clientes cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private static void agregarCliente(Connection connection, Scanner scanner) {
        Clientes cliente = new Clientes();
        // Solicitar los datos del cliente al usuario y asignarlos a las propiedades del objeto cliente
        // ...
        try {
            cliente.save(connection); // Guardar el nuevo cliente en la base de datos
            System.out.println("El cliente ha sido añadido exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al añadir el cliente: " + e.getMessage());
        }
    }

    private static void editarCliente(Connection connection, Scanner scanner) throws SQLException {
        // Solicitar al usuario el código interno del cliente a editar
        System.out.print("Ingrese el código interno del cliente a editar: ");
        int codigoInterno = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        // Buscar el cliente en la base de datos utilizando el código interno
        Clientes cliente = Clientes.buscarClientePorCodigoInterno(connection, codigoInterno);
        if (cliente != null) {
            // Mostrar los datos actuales del cliente
            System.out.println("Datos actuales del cliente:");
            System.out.println(cliente);

            // Solicitar los nuevos datos del cliente al usuario y asignarlos a las propiedades del objeto cliente
            // ...

            try {
                cliente.update(connection); // Actualizar los datos del cliente en la base de datos
                System.out.println("El cliente ha sido actualizado exitosamente.");
            } catch (SQLException e) {
                System.out.println("Error al editar el cliente: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró ningún cliente con el código interno proporcionado.");
        }
    }

    private static void borrarCliente(Connection connection, Scanner scanner) throws SQLException {
        // Solicitar al usuario el código interno del cliente a borrar
        System.out.print("Ingrese el código interno del cliente a borrar: ");
        int codigoInterno = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        // Buscar el cliente en la base de datos utilizando el código interno
        Clientes cliente = Clientes.buscarClientePorCodigoInterno(connection, codigoInterno);
        if (cliente != null) {
            // Mostrar los datos del cliente antes de borrarlo
            System.out.println("Datos del cliente a borrar:");
            System.out.println(cliente);

            // Confirmar la acción de borrado al usuario
            System.out.print("¿Está seguro de que desea borrar este cliente? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();
            if (confirmacion.equals("s")) {
                try {
                    cliente.delete(connection); // Borrar el cliente de la base de datos
                    System.out.println("El cliente ha sido borrado exitosamente.");
                } catch (SQLException e) {
                    System.out.println("Error al borrar el cliente: " + e.getMessage());
                }
            } else {
                System.out.println("Operación de borrado cancelada.");
            }
        } else {
            System.out.println("No se encontró ningún cliente con el código interno proporcionado.");
        }
    }

    // Implementa los métodos correspondientes para mostrar, añadir, editar y borrar registros en la tabla de Compras

    private static void menuCompras(Connection connection, Scanner scanner) {
        int opcion;
        do {
            System.out.println("=== MENÚ COMPRAS ===");
            System.out.println("1. Mostrar compras");
            System.out.println("2. Añadir compra");
            System.out.println("3. Editar compra");
            System.out.println("4. Borrar compra");
            System.out.println("0. Volver al menú principal");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    mostrarCompras(connection);
                    break;
                case 2:
                    agregarCompra(connection, scanner);
                    break;
                case 3:
                    editarCompra(connection, scanner);
                    break;
                case 4:
                    borrarCompra(connection, scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    private static void mostrarCompras(Connection connection) {
        Set<Compras> compras = Compras.mostrarCompras(connection);
        System.out.println("=== LISTA DE COMPRAS ===");
        for (Compras compra : compras) {
            System.out.println(compra);
        }
    }

    private static void agregarCompra(Connection connection, Scanner scanner) {
        Compras compra = new Compras();
        // Solicitar los datos de la compra al usuario y asignarlos a las propiedades del objeto compra
        // ...
        try {
            compra.save(connection); // Guardar la nueva compra en la base de datos
            System.out.println("La compra ha sido añadida exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al añadir la compra: " + e.getMessage());
        }
    }

    private static void editarCompra(Connection connection, Scanner scanner) {
        // Solicitar al usuario el ID de la compra a editar
        System.out.print("Ingrese el ID de la compra a editar: ");
        int idCompra = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        // Buscar la compra en la base de datos utilizando el ID
        Compras compra = Compras.buscarCompraPorID(connection, idCompra);
        if (compra != null) {
            // Mostrar los datos actuales de la compra
            System.out.println("Datos actuales de la compra:");
            System.out.println(compra);

            // Solicitar los nuevos datos de la compra al usuario y asignarlos a las propiedades del objeto compra
            // ...

            try {
                compra.update(connection); // Actualizar los datos de la compra en la base de datos
                System.out.println("La compra ha sido actualizada exitosamente.");
            } catch (SQLException e) {
                System.out.println("Error al editar la compra: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró ninguna compra con el ID proporcionado.");
        }
    }

    private static void borrarCompra(Connection connection, Scanner scanner) {
        // Solicitar al usuario el ID de la compra a borrar
        System.out.print("Ingrese el ID de la compra a borrar: ");
        int idCompra = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        // Buscar la compra en la base de datos utilizando el ID
        Compras compra = Compras.buscarCompraPorID(connection, idCompra);
        if (compra != null) {
            // Mostrar los datos de la compra antes de borrarla
            System.out.println("Datos de la compra a borrar:");
            System.out.println(compra);

            // Confirmar la acción de borrado al usuario
            System.out.print("¿Está seguro de que desea borrar esta compra? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();
            if (confirmacion.equals("s")) {
                try {
                    compra.delete(connection); // Borrar la compra de la base de datos
                    System.out.println("La compra ha sido borrada exitosamente.");
                } catch (SQLException e) {
                    System.out.println("Error al borrar la compra: " + e.getMessage());
                }
            } else {
                System.out.println("Operación de borrado cancelada.");
            }
        } else {
            System.out.println("No se encontró ninguna compra con el ID proporcionado.");
        }
    }

    // Implementa los métodos correspondientes para mostrar, añadir, editar y borrar registros en la tabla de Revisiones

   private static void menuRevisiones(Connection connection, Scanner scanner) throws SQLException {
    int opcion;
    do {
        System.out.println("=== MENÚ REVISIONES ===");
        System.out.println("1. Mostrar revisiones");
        System.out.println("2. Añadir revisión");
        System.out.println("3. Editar revisión");
        System.out.println("4. Borrar revisión");
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingrese una opción: ");
        opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

       switch (opcion) {
 case 1:
    Revisiones.mostrarRevision(connection);
    break;
    case 2:
        Revisiones revision = new Revisiones();
        revision.anadirRevision(connection);
        break;
    case 3:
        revision = new Revisiones();
        revision.editarRevision(connection);
        break;
    case 4:
        revision = new Revisiones();
        revision.borrarRevision(connection);
        break;
    case 0:
        System.out.println("Volviendo al menú principal...");
        break;
    default:
        System.out.println("Opción inválida. Intente nuevamente.");
        }
        System.out.println();
    } while (opcion != 0);
}
}
    