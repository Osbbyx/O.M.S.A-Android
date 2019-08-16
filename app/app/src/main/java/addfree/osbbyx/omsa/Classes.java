package addfree.osbbyx.omsa;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Classes {

    String nombre, telefono, edad,id;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String email = user.getEmail().toString();
    String adss;


    public Classes(String id,String email, String nombre, String telefono, String edad, String adss) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.adss = adss;
    }

    public Classes(String email, String nombre, String telefono, String edad, String adss) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.adss = adss;
    }
    public String getId(){
        return id;
    }

    public String getEmaill(){
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEdad() {
        return edad;
    }
    public String getAdss(){
        return adss;
    }
}
