package ser13;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

//ACCESO A BD db4o
public class AccesoDatos {
  
  static ObjectContainer db;
  
  // Constructor
  public AccesoDatos() {
      db = Conexion.getDBConexion();
  }
  
  /* * Dispone del método procesarCadena() que recibe en un String el departamento a consultar. 
   * Lo pasa a entero. Usa el método queryByExample() para obtener el departamento deseado, 
   * si lo encuentra devolverá un objeto Departamentos y si no existe devolverá null:
   */
  
  // Se procesa la cadena que manda el hilo con el dep a localizar
  
  //La palabra clave synchronized es crucial: garantiza que solo un hilo a la vez pueda ejecutar este método. 
  //Esto es esencial para prevenir problemas de concurrencia (como condiciones de carrera) 
  //si varios hilos intentan acceder o modificar la base de datos simultáneamente.
  synchronized Departamentos procesarCadena(String str) {
      int i;
      Departamentos d = null;
      try {
          i = Integer.parseInt(str);
      } catch (NumberFormatException n) {
          System.out.println("<<DEPARTAMENTO: " + str + 
                  " INCORRECTO>>");
          return d;
      }
      
      //Se crea un objeto Departamentos incompleto (llamado un "objeto plantilla"). 
      //db4o utiliza la técnica de Consulta por Ejemplo (Query by Example o QBE) para buscar objetos 
      //que coincidan con los campos que no son null de la plantilla.
      Departamentos dep = new Departamentos(i, null, null, null);
      ObjectSet<Departamentos> result = db.queryByExample(dep);
      
      if (result.size() == 0) {
          System.out.println("<<DEPARTAMENTO: " + i + " NO EXISTE>>");
      } else {
          d = result.next();
      }
      
      return d; // devuelve un objeto Departamentos
  }
  
} //..fin AccesoDatos
