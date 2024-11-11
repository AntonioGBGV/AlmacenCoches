import model.Coche;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorAlmacenCoches {
    public String path = "src/main/java/resources/coches.dat";
    public String path2 = "src/main/java/resources/coches.csv";

    private ArrayList<Coche> coches;

    public void ejecutar() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Añadir nuevo coche");
            System.out.println("2. Borrar coche por ID");
            System.out.println("3. Consultar coche por ID");
            System.out.println("4. Listado de coches");
            System.out.println("5. Exportar coches a CSV");
            System.out.println("6. Cerrar el programa");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> anadirCoche();
                case 2 -> borrarCochePorId();
                case 3 -> consultarCochePorId();
                case 4 -> listarCoches();
                case 5 -> exportarCoches();
                case 6 -> {
                    guardarCoches();
                    System.out.println("Programa terminado. Datos guardados en " + path);
                }
                default -> System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 6);
    }

    public GestorAlmacenCoches() {
        coches = new ArrayList<>();
        cargarCoches();
    }

    public void cargarCoches() {
        File file = new File(path);
        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                coches = (ArrayList<Coche>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al cargar coches: " + e.getMessage());
            }
        }
    }

    public void guardarCoches() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(coches);
        } catch (IOException e) {
            System.out.println("Error al guardar coches: " + e.getMessage());
        }
    }

    public void anadirCoche() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduzca el ID del coche: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Coche coche : coches) {
            if (coche.getId() == id) {
                System.out.println("Ya existe un coche con el mismo ID.");
                return;
            }
        }
        System.out.print("Introduzca la matrícula: ");
        String matricula = sc.nextLine();
        for (Coche coche : coches) {
            if (coche.getMatricula().equals(matricula)) {
                System.out.println("Ya existe un coche con la misma matrícula.");
                return;
            }
        }
        System.out.print("Introduzca la marca: ");
        String marca = sc.nextLine();
        System.out.print("Introduzca el modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Introduzca el color: ");
        String color = sc.nextLine();
        coches.add(new Coche(id, matricula, marca, modelo, color));
        System.out.println("Coche añadido!");
    }

    public void borrarCochePorId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduzca el ID del coche a borrar: ");
        int id = sc.nextInt();
        for (Coche coche : coches) {
            if (coche.getId() == id) {
                coches.remove(coche);
                System.out.println("Coche con ID " + id + " ha sido borrado.");
                return;
            }
        }
        System.out.println("No se encontró un coche con el ID: " + id);
    }

    public void consultarCochePorId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduzca el ID del coche a consultar: ");
        int id = sc.nextInt();
        for (Coche coche : coches) {
            if (coche.getId() == id) {
                System.out.println("Coche encontrado: " + coche);
                return;
            }
        }
        System.out.println("Coche no encontrado.");
    }

    public void listarCoches() {
        if (coches.isEmpty()) {
            System.out.println("No hay coches en el almacén.");
        } else {
            System.out.println("\nEl listado de coches es: ");
            coches.forEach(System.out::println);
        }
    }

    public void exportarCoches() {
        File file = new File(path2);
        PrintWriter printWriter = null;
        try{
            printWriter = new PrintWriter(new FileWriter(file, false));
            printWriter.println("ID;Matricula;Marca;Modelo;Color");
            for (Coche coche : coches) {
                printWriter.println(coche.getId() + ";" + coche.getMatricula() + ";" + coche.getMarca()
                        + ";" + coche.getModelo() + ";" + coche.getColor());
            }
            System.out.println("Datos exportados a coches.csv correctamente.");
        } catch (IOException e) {
            System.out.println("Error al exportar a CSV: " + e.getMessage());
        }finally {
            try {
                printWriter.close();
            } catch(NullPointerException e){
                System.out.println("Error al escribir en el fichero: " + e.getMessage());
            }
        }
    }
}
