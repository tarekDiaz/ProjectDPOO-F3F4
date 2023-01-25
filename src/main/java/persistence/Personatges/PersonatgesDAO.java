package persistence.Personatges;

import business.Personatge.Personatge;

import java.util.List;

public interface PersonatgesDAO {

    public void nouPersonatge(Personatge personatge);

    public List<Personatge> readPersonatge();

    public void borrar(String nom);
}
