package ma.enset.hospitaltp3.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.hospitaltp3.entities.Patient;
import ma.enset.hospitaltp3.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "5") int s,
                        @RequestParam(name = "keyword", defaultValue = "") String kw
                        ){
        // Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(p, s));
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(p, s));
        List<Patient> patients = pagePatients.getContent();
        model.addAttribute("listPatients", patients);
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", p);
        model.addAttribute("keyword", kw);
        return "patients";
    }

    @GetMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "page", defaultValue = "0")int p,
                         @RequestParam(name = "keyword", defaultValue = "") String kw){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+p+"&keyword="+kw;
    }

    @GetMapping("/user/patients")
    @ResponseBody
    public List<Patient> listPatient(){
        return patientRepository.findAll();
    }
    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }
    @PostMapping(path = "/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        model.addAttribute("page");
        model.addAttribute("keyword");
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/editPatient")
    public String editPatient(Model model, Long id,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "") String keyword){
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient", patient);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editPatient";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }


}
