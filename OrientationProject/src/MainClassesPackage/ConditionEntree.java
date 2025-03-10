package MainClassesPackage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class ConditionEntree implements Serializable {
	
	int ageMax = 0;
	
	float noteMinPara = 0;//pour parallele
	int classementUnivMin; //pour bac+3
	
	//pour prepa
	int classementMinMp=0;
	int classementMinPsi=0;
	int classementMinTsi=0;
	int classementMinEc=0;
	
	
	public ConditionEntree(int ageMax, float noteMinPara, int classementUnivMin,int classementMinMp, int classementMinPsi, int classementMinTsi, int classementMinEc) {
		this.ageMax = ageMax;
		this.noteMinPara = noteMinPara;
		this.classementUnivMin = classementUnivMin;
		this.classementMinMp = classementMinMp;
		this.classementMinPsi = classementMinPsi;
		this.classementMinTsi = classementMinTsi;
		this.classementMinEc = classementMinEc;
	}
	
	@Override 
	public String toString() {
		return " age : "+this.ageMax+"\n note min parallele : "+this.noteMinPara +"\n classementunivMin : "+this.classementUnivMin+"\n classement min mp/psi/tsi/ec :"+this.classementMinMp+" "+this.classementMinPsi+" "+this.classementMinTsi+" "+this.classementMinEc;
	}

}
	