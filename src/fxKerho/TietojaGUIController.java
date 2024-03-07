package fxKerho;


import fi.jyu.mit.fxgui.ModalControllerInterface;


/**
 * @author Miia Arkko
 * @version 10.2.2023
 *
 */
public class TietojaGUIController  implements ModalControllerInterface<String> {
    
    @Override
    public String getResult() {
        return vastaus;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
    
    
    // ===================================================
    
    private String vastaus = null;
    
    
    
}