package rreeggkk.nuclearsciences.common.element;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.nuclear.decay.AlphaDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.BetaDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.ITDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.MultiDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.SpecOutDecay;
import rreeggkk.nuclearsciences.common.nuclear.element.BasicElement;
import rreeggkk.nuclearsciences.common.nuclear.element.Isotope;
import rreeggkk.nuclearsciences.common.nuclear.element.MetastableIsotope;
import rreeggkk.nuclearsciences.common.util.TimeConversion;

/**
 * Thermal Cross sections from http://www.nndc.bnl.gov/atlas/atlasvalues.html 
 * Capture cross sections for elements that would capture into nothing have been removed
 */
public class ElementRegister {
	public static void register() {
		//252 has SF decay
		new BasicElement("Californium", "Cf", 98, 25) //Heat capacity not found; Law of Dulong and Petit used
			.addIsotope(new Isotope<BasicElement>(252-98, 0, 32).setAtomicMass(252.081626).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.645))) //Capture = 20.4
			.addIsotope(new Isotope<BasicElement>(251-98, 2850, 4895).setAtomicMass(251.079587).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(900.6)))
			.addIsotope(new Isotope<BasicElement>(249-98, 0, 1642).setAtomicMass(249.0748535).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(351))); //Capture = 497

		//238 has SF decay
		new BasicElement("Curium", "Cm", 96, 25) //Heat capacity not found; Law of Dulong and Petit used
			.addIsotope(new Isotope<BasicElement>(248-96, 0, 0.37).setAtomicMass(248.072349).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(3.4e5))) //Capture = 2.63
			.addIsotope(new Isotope<BasicElement>(247-96, 57, 81.9).setAtomicMass(247.070354).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.565e7)))
			.addIsotope(new Isotope<BasicElement>(245-96, 0, 2144).setAtomicMass(245.0654912).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(8.5e3))); //Capture = 369

		new BasicElement("Americium", "Am", 95, 62.7)
			.addIsotope(new Isotope<BasicElement>(243-95, 0, 0.1983).setAtomicMass(243.0613811).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7388))) //Capture = 75.1
			.addIsotope(new Isotope<BasicElement>(241-95, 0, 3.2).setAtomicMass(241.0568291).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(432.7))); //Capture = 587

		new BasicElement("Plutonium", "Pu", 94, 35.5)
			.addIsotope(new Isotope<BasicElement>(244-94, 0, 0).setAtomicMass(244.064204).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(8e7))) //Fission Cross Section not found; Capture = 1.7
			.addIsotope(new Isotope<BasicElement>(243-94, 87, 196).setAtomicMass(243.062003).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(4.9563)))
			.addIsotope(new Isotope<BasicElement>(241-94, 0, 1011.1).setAtomicMass(241.0568515).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(14.290))) //Capture = 362.1
			.addIsotope(new Isotope<BasicElement>(240-94, 289.5, 0.056).setAtomicMass(240.0538135).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(6561)))
			.addIsotope(new Isotope<BasicElement>(239-94, 269.3, 748.1).setAtomicMass(239.0521634).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(24100)))
			.addIsotope(new Isotope<BasicElement>(238-94, 540, 17.9).setAtomicMass(238.0495599).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(87.7)));
		
		new BasicElement("Neptunium", "Np", 93, 29.46)
			.addIsotope(new Isotope<BasicElement>(240-93, 0, 0).setAtomicMass(240.056162).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(1.032))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(239-93, 68, 0).setAtomicMass(239.0529390).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(2.356))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(237-93, 0, 0.02).setAtomicMass(237.0481734).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.14e6))); //Capture = 175.9

		new BasicElement("Uranium", "U", 92, 27.665)
			.addIsotope(new Isotope<BasicElement>(240-92, 0, 0).setAtomicMass(240.056592).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(14.1))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(239-92, 22, 14).setAtomicMass(239.0542933).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(23.45)))
			.addIsotope(new Isotope<BasicElement>(238-92, 2.68, 0.000003).setAtomicMass(238.0507882).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(4.468e9)))
			.addIsotope(new Isotope<BasicElement>(237-92, 443, 0.35).setAtomicMass(237.0487302).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(6.75))) //Fission is <0.35
			.addIsotope(new Isotope<BasicElement>(236-92, 5.09, 0.066).setAtomicMass(236.045568).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.3e7)))
			.addIsotope(new Isotope<BasicElement>(235-92, 98.8, 582.6).setAtomicMass(235.0439299).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7038e7)))
			.addIsotope(new Isotope<BasicElement>(234-92, 99.8, 0.067).setAtomicMass(234.0409521).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.455e5)))
			.addIsotope(new Isotope<BasicElement>(233-92, 45.5, 529.1).setAtomicMass(233.0396352).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.592e5)));

		//234 and 234m Cross sections not found
		new BasicElement("Protactinium", "Pa", 91, 22.9)
			.addIsotope(new MetastableIsotope<BasicElement>(234-91, 0, 0, 0).setAtomicMass(234.043308).setDecay(new MultiDecay<MetastableIsotope<BasicElement>>(new BetaDecay<MetastableIsotope<BasicElement>>(), new Apfloat(0.9983), new ITDecay<MetastableIsotope<BasicElement>>(), new Apfloat(0.0016)), TimeConversion.minToTick(1.17)))
			.addIsotope(new Isotope<BasicElement>(234-91, 0, 0).setAtomicMass(234.043308).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(6.70)))
			.addIsotope(new Isotope<BasicElement>(233-91, 39.5, 0.1).setAtomicMass(233.0402473).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(26.975))) //Fission is <0.1
			.addIsotope(new Isotope<BasicElement>(231-91, 0, 0.02).setAtomicMass(231.0358840).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(3.276e4))); //Capture = 200.6

		new BasicElement("Thorium", "Th", 90, 26.23)
			.addIsotope(new Isotope<BasicElement>(234-90, 0, 0.01).setAtomicMass(234.043601).setDecay(new SpecOutDecay<Isotope<BasicElement>>("Protactinium-234m"), TimeConversion.dayToTick(24.10))) //Fission is <0.01; Capture = 1.8
			.addIsotope(new Isotope<BasicElement>(233-90, 1330, 15).setAtomicMass(233.0415818).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(21.83))) //TODO: Maybe ill change the capture to make it more interesting
			.addIsotope(new Isotope<BasicElement>(232-90, 7.35, 0.000052).setAtomicMass(232.0380553).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.405e10)))
			.addIsotope(new Isotope<BasicElement>(231-90, 0, 0).setAtomicMass(231.0363043).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(25.5))) //Cross Sections not found
			.addIsotope(new Isotope<BasicElement>(230-90, 22.9, 0).setAtomicMass(230.0331338).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7.538e4))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(229-90, 62.8, 30.8).setAtomicMass(229.031762).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7340)))
			.addIsotope(new Isotope<BasicElement>(228-90, 123, 0.3).setAtomicMass(228.0287411).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.9116))) //Fission is <0.3
			.addIsotope(new Isotope<BasicElement>(227-90, 0, 202).setAtomicMass(227.0277041).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(18.68))); //Capture not found

		new BasicElement("Actinium", "Ac", 89, 27.2)
			.addIsotope(new Isotope<BasicElement>(228-89, 0, 0).setAtomicMass(228.0310211).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(6.13))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(227-89, 890, 0).setAtomicMass(227.0277521).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.9861), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.0139)), TimeConversion.yearToTick(21.772))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(225-89, 0, 0).setAtomicMass(225.023230).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(10.0))); //Cross sections not found

		new BasicElement("Radium", "Ra", 88, 20.8)
			.addIsotope(new Isotope<BasicElement>(228-88, 0, 0).setAtomicMass(228.0310703).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(5.75))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(226-88, 0, 0).setAtomicMass(226.0254098).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1600))) //Fission not found; Capture = 12.8
			.addIsotope(new Isotope<BasicElement>(225-88, 0, 0).setAtomicMass(225.023612).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(14.9))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(224-88, 12, 0).setAtomicMass(224.0202118).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(3.6319))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(223-88, 130, 0).setAtomicMass(223.0185022).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(11.43))); //Fission not found

		//Cross sectional data not found
		new BasicElement("Francium", "Fr", 87, 31.2228) //Heat capacity calculated manually from 140 J/kgK specific heat capacity and 223.020 g/mol avg atomic mass
			.addIsotope(new Isotope<BasicElement>(223-87, 0, 0).setAtomicMass(223.0197359).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.9999), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.0001)), TimeConversion.minToTick(22)))
			.addIsotope(new Isotope<BasicElement>(221-87, 0, 0).setAtomicMass(221.014255).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(4.8)));

		//Cross sectional data not found
		new BasicElement("Radon", "Rn", 86, 20.786)
			.addIsotope(new Isotope<BasicElement>(222-86, 0, 0).setAtomicMass(222.0175777).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(3.8235)))
			.addIsotope(new Isotope<BasicElement>(220-86, 0, 0).setAtomicMass(220.0113940).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(55.6)))
			.addIsotope(new Isotope<BasicElement>(219-86, 0, 0).setAtomicMass(219.0094802).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(3.96)))
			.addIsotope(new Isotope<BasicElement>(218-86, 0, 0).setAtomicMass(218.0056013).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(35e-3)));

		//Cross sectional data not found
		new BasicElement("Astatine", "At", 85, 29.39818) //Heat capacity calculated manually from 140 J/kgK specific heat capacity and 209.987 g/mol avg atomic mass
			.addIsotope(new Isotope<BasicElement>(219-85, 0, 0).setAtomicMass(219.011162).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.97), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.03)), TimeConversion.secToTick(56)))
			.addIsotope(new Isotope<BasicElement>(218-85, 0, 0).setAtomicMass(218.008694).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.9990), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.0010)), TimeConversion.secToTick(1.5)))
			.addIsotope(new Isotope<BasicElement>(217-85, 0, 0).setAtomicMass(217.004719).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(32e-6)))
			.addIsotope(new Isotope<BasicElement>(215-85, 0, 0).setAtomicMass(214.998653).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1e-4)));

		//Cross sectional data not found
		new BasicElement("Polonium", "Po", 84, 26.4)
			.addIsotope(new Isotope<BasicElement>(218-84, 0, 0).setAtomicMass(218.0089730).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.9998), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.0002)), TimeConversion.minToTick(3.10)))
			.addIsotope(new Isotope<BasicElement>(216-84, 0, 0).setAtomicMass(216.0019150).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.145)))
			.addIsotope(new Isotope<BasicElement>(215-84, 0, 0).setAtomicMass(214.9994200).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.97), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.03)), TimeConversion.secToTick(1.781e-3)))
			.addIsotope(new Isotope<BasicElement>(214-84, 0, 0).setAtomicMass(213.9952014).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(164.3e-6)))
			.addIsotope(new Isotope<BasicElement>(213-84, 0, 0).setAtomicMass(212.992857).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(3.2e-6)))
			.addIsotope(new Isotope<BasicElement>(212-84, 0, 0).setAtomicMass(211.9888680).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(299e-9)))
			.addIsotope(new Isotope<BasicElement>(211-84, 0, 0).setAtomicMass(210.9866532).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.516)))
			.addIsotope(new Isotope<BasicElement>(210-84, 0, 0).setAtomicMass(209.9828737).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(138.376)));

		//Cross sectional data not found for all but 209
		new BasicElement("Bismuth", "Bi", 83, 25.52)
			.addIsotope(new Isotope<BasicElement>(215-83, 0, 0).setAtomicMass(215.001770).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(7.6)))
			.addIsotope(new Isotope<BasicElement>(214-83, 0, 0).setAtomicMass(213.998712).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.99979), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.00021)), TimeConversion.minToTick(19.9)))
			.addIsotope(new Isotope<BasicElement>(213-83, 0, 0).setAtomicMass(212.994385).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.9780), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.0220)), TimeConversion.minToTick(46.5)))
			.addIsotope(new Isotope<BasicElement>(212-83, 0, 0).setAtomicMass(211.9912857).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.6406), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.3594)), TimeConversion.minToTick(60.55)))
			.addIsotope(new Isotope<BasicElement>(211-83, 0, 0).setAtomicMass(210.987269).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.9972), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.0028)), TimeConversion.minToTick(2.14)))
			.addIsotope(new Isotope<BasicElement>(210-83, 0, 0).setAtomicMass(209.9841204).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(5.012)))
			.addIsotope(new Isotope<BasicElement>(209-83, 0.0342, 0).setAtomicMass(208.9803987).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.9e19))); //Fission not found

		new BasicElement("Lead", "Pb", 82, 26.650)
			.addIsotope(new Isotope<BasicElement>(214-82, 0, 0).setAtomicMass(213.9998054).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(26.8))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(212-82, 0, 0).setAtomicMass(211.9918975).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(10.64))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(211-82, 0, 0).setAtomicMass(210.9887370).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(36.1))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(210-82, 0, 0).setAtomicMass(209.9841885).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(22.20))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(209-82, 0, 0).setAtomicMass(208.9810901).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(3.25))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(208-82, 0.00023, 0).setAtomicMass(206.9758969))
			.addIsotope(new Isotope<BasicElement>(207-82, 0.622, 0).setAtomicMass(206.9758969))
			.addIsotope(new Isotope<BasicElement>(206-82, 26.6, 0).setAtomicMass(205.9744653));

		//Cross sectional data not found
		new BasicElement("Thallium", "Tl", 81, 26.32)
			.addIsotope(new Isotope<BasicElement>(210-81, 0, 0).setAtomicMass(209.990074).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(1.30)))
			.addIsotope(new Isotope<BasicElement>(209-81, 0, 0).setAtomicMass(208.985359).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(2.161)))
			.addIsotope(new Isotope<BasicElement>(208-81, 0, 0).setAtomicMass(207.9820187).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(3.035)))
			.addIsotope(new Isotope<BasicElement>(207-81, 0, 0).setAtomicMass(206.977419).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(4.77)))
			.addIsotope(new Isotope<BasicElement>(205-81, 0, 0).setAtomicMass(204.9744275));

		new BasicElement("Helium", "He", 2, 20.78)
			.addIsotope(new Isotope<BasicElement>(4-2, 0, 0).setAtomicMass(4.00260325415)); //Cross sections not found
	}
}

