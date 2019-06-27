
import com.tsoft.metamodel.HibernateEntityProperties;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

@Service
public class EtlReader implements FieldSetMapper {
    @Autowired
    private HibernateEntityProperties hep;
    private String categorie;

    public EtlReader() {
    }

    public String getCategorie() {
        return this.categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Object mapFieldSet(FieldSet fs) throws BindException {
        return null;
    }
}
