package ser13;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class Conexion {

	final static String BDPer = "EMPLEDEP.YAP";
	
	//static ObjectContainer db;: La conexión a la base de datos se mantiene en un atributo estático, 
	//lo que significa que solo existe una copia para toda la aplicación.
	static ObjectContainer db;

	//Bloque Estático (static {}): Este bloque se ejecuta solo una vez cuando la clase Conexion se carga
	//por primera vez en memoria. Es aquí donde se abre el archivo de la base de datos (Db4oEmbedded.openFile(...))
	//y se asigna a la variable db.
	
	static {
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);
	}

	//getDBConexion(): Este método estático proporciona el punto de acceso global a la conexión
	//de base de datos (db). Cualquier parte del código puede llamar a Conexion.getDBConexion() 
	//para obtener el objeto ObjectContainer y trabajar con la base de datos.
	public static ObjectContainer getDBConexion() {
		return db;
	}

} // Fin Conexion