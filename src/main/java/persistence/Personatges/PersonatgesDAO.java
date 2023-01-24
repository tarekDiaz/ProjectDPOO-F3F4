package persistence.Personatges;

import business.Personatge;

import java.util.List;

public interface PersonatgesDAO {

    public void nouPersonatge(Personatge personatge);

    public List<Personatge> readPersonatge();

    public void borrar(String nom);
}
