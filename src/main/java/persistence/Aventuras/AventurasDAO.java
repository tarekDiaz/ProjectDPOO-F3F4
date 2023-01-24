package persistence.Aventuras;

import business.Aventura;

import java.util.List;

public interface AventurasDAO {

    public List<Aventura> readAventura();

    public void writeAventura (Aventura aventura);

}

