package rreeggkk.nuclearsciences.common.element;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.nuclear.decay.AlphaDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.BetaDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.DoubleBetaDecay;
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
		
		//HIGH DECAY CHAINS
		
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

		
		//BIG DECAY PRODUCTS
		//Fission not found
		new BasicElement("Gadolinium", "Gd", 64, 37.03)
			.addIsotope(new Isotope<BasicElement>(155-64, 0, 0).setAtomicMass(154.9226220)); //Capture = 60900

		//Fission not found
		new BasicElement("Europium", "Eu", 63, 27.66)
			.addIsotope(new Isotope<BasicElement>(155-63, 0, 0).setAtomicMass(154.9228933).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(4.7611))) //Capture = 3950
			.addIsotope(new Isotope<BasicElement>(153-63, 0, 0).setAtomicMass(152.9212303)) //Capture = 312
			.addIsotope(new Isotope<BasicElement>(151-63, 0, 0).setAtomicMass(150.9198502).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(4.62e18))); //Capture = 9200

		new BasicElement("Samarium", "Sm", 62, 29.54)
			.addIsotope(new Isotope<BasicElement>(155-62, 0, 0).setAtomicMass(154.9246402).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(46.294))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(154-62, 8.3, 0).setAtomicMass(153.9222093)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(153-62, 0, 0).setAtomicMass(152.9220974).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(22.3))) //Cross sections not found
			.addIsotope(new Isotope<BasicElement>(152-62, 206, 0).setAtomicMass(151.9197324)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(151-62, 15170, 0).setAtomicMass(150.9199324).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(88.8))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(150-62, 100, 0).setAtomicMass(149.9172755)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(149-62, 40140, 0).setAtomicMass(148.9171847)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(147-62, 0, 0).setAtomicMass(146.9148979).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.06e11))); //Capture = 57

		//Cross sectional data not found
		new BasicElement("Promethium", "Pm", 61, 24.3)
			.addIsotope(new Isotope<BasicElement>(155-61, 0, 0).setAtomicMass(154.92810).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(41.5)))
			.addIsotope(new Isotope<BasicElement>(154-61, 0, 0).setAtomicMass(153.92646).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(1.73)))
			.addIsotope(new Isotope<BasicElement>(153-61, 0, 0).setAtomicMass(152.924117).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(5.25)))
			.addIsotope(new Isotope<BasicElement>(152-61, 0, 0).setAtomicMass(151.923497).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(4.12)))
			.addIsotope(new Isotope<BasicElement>(151-61, 700, 0).setAtomicMass(150.921207).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(28.40))) //Capture = <700, 700 used
			.addIsotope(new Isotope<BasicElement>(149-61, 0, 0).setAtomicMass(148.918334).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(53.08))) //Capture = 1400
			.addIsotope(new Isotope<BasicElement>(147-61, 0, 0).setAtomicMass(146.9151385).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.6234))); //Capture = 2064

		//Most cross sectional data not found
		new BasicElement("Neodymium", "Nd", 60, 27.45)
			.addIsotope(new Isotope<BasicElement>(153-60, 0, 0).setAtomicMass(152.927698).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(31.6)))
			.addIsotope(new Isotope<BasicElement>(152-60, 0, 0).setAtomicMass(151.924682).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(11.4)))
			.addIsotope(new Isotope<BasicElement>(151-60, 0, 0).setAtomicMass(150.923829).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(12.44)))
			.addIsotope(new Isotope<BasicElement>(150-60, 1.04, 0).setAtomicMass(149.920891).setDecay(new DoubleBetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(6.7e18))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(149-60, 0, 0).setAtomicMass(148.920149).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(1.728)))
			.addIsotope(new Isotope<BasicElement>(148-60, 2.58, 0).setAtomicMass(147.916893)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(147-60, 440, 0).setAtomicMass(146.9161004).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(10.98))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(146-60, 1.49, 0).setAtomicMass(145.9131169)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(145-60, 50, 0).setAtomicMass(144.9125736)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(144-60, 3.6, 0).setAtomicMass(143.9100873).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.29e15))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(143-60, 325, 0).setAtomicMass(142.9098143)); //Fission not found

		//Cross sectional data not found
		new BasicElement("Praseodymium", "Pr", 59, 27.20)
			.addIsotope(new Isotope<BasicElement>(150-59, 0, 0).setAtomicMass(149.926673).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(6.19)))
			.addIsotope(new Isotope<BasicElement>(149-59, 0, 0).setAtomicMass(148.92372).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(2.26)))
			.addIsotope(new Isotope<BasicElement>(148-59, 0, 0).setAtomicMass(147.922135).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(2.29)))
			.addIsotope(new Isotope<BasicElement>(147-59, 0, 0).setAtomicMass(146.918996).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(13.4)))
			.addIsotope(new Isotope<BasicElement>(146-59, 0, 0).setAtomicMass(145.91764).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(24.15)))
			.addIsotope(new Isotope<BasicElement>(145-59, 0, 0).setAtomicMass(144.914512).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(5.984)))
			.addIsotope(new Isotope<BasicElement>(144-59, 0, 0).setAtomicMass(143.913305).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(17.28)))
			.addIsotope(new Isotope<BasicElement>(143-59, 90, 0).setAtomicMass(142.9108169).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(13.57))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(141-59, 0, 0).setAtomicMass(140.9076528)); //Capture = 11.5

		//Cross sectional data not found
		new BasicElement("Cerium", "Ce", 58, 26.94)
			.addIsotope(new Isotope<BasicElement>(149-58, 0, 0).setAtomicMass(148.9284).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(5.3)))
			.addIsotope(new Isotope<BasicElement>(148-58, 0, 0).setAtomicMass(147.92443).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(56)))
			.addIsotope(new Isotope<BasicElement>(147-58, 0, 0).setAtomicMass(146.92267).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(56.4)))
			.addIsotope(new Isotope<BasicElement>(146-58, 0, 0).setAtomicMass(145.91876).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(13.52)))
			.addIsotope(new Isotope<BasicElement>(145-58, 0, 0).setAtomicMass(144.91723).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(3.01)))
			.addIsotope(new Isotope<BasicElement>(144-58, 1, 0).setAtomicMass(143.913647).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(284.91))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(143-58, 6, 0).setAtomicMass(142.912386).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(33.039))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(142-58, 0.97, 0).setAtomicMass(141.909244)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(141-58, 29, 0).setAtomicMass(140.9082763).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(32.508))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(140-58, 0.58, 0).setAtomicMass(139.9054387)); //Fission not found

		//Cross sectional data not found
		new BasicElement("Lanthanum", "La", 57, 27.11)
			.addIsotope(new Isotope<BasicElement>(146-57, 0, 0).setAtomicMass(145.92579).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(6.27)))
			.addIsotope(new Isotope<BasicElement>(145-57, 0, 0).setAtomicMass(144.92165).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(24.8)))
			.addIsotope(new Isotope<BasicElement>(144-57, 0, 0).setAtomicMass(143.91960).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(40.8)))
			.addIsotope(new Isotope<BasicElement>(143-57, 0, 0).setAtomicMass(142.916063).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(14.2)))
			.addIsotope(new Isotope<BasicElement>(142-57, 0, 0).setAtomicMass(141.914079).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(91.1)))
			.addIsotope(new Isotope<BasicElement>(141-57, 0, 0).setAtomicMass(140.910962).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(3.92)))
			.addIsotope(new Isotope<BasicElement>(140-57, 2.7, 0).setAtomicMass(139.9094776).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(1.6781))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(139-57, 9.04, 0).setAtomicMass(138.9063533)); //Fission not found

		//Most cross sectional data not found
		new BasicElement("Barium", "Ba", 56, 28.07)
			.addIsotope(new Isotope<BasicElement>(144-56, 0, 0).setAtomicMass(143.922953).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(11.5)))
			.addIsotope(new Isotope<BasicElement>(143-56, 0, 0).setAtomicMass(142.920627).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(14.5)))
			.addIsotope(new Isotope<BasicElement>(142-56, 0, 0).setAtomicMass(141.916453).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(10.6)))
			.addIsotope(new Isotope<BasicElement>(141-56, 0, 0).setAtomicMass(140.914411).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(18.27)))
			.addIsotope(new Isotope<BasicElement>(140-56, 1.6, 0).setAtomicMass(139.910605).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(12.752))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(139-56, 0, 0).setAtomicMass(138.9088413).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(83.06)))
			.addIsotope(new Isotope<BasicElement>(138-56, 0.404, 0).setAtomicMass(137.9052472)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(137-56, 3.6, 0).setAtomicMass(136.9058274)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(136-56, 0.68, 0).setAtomicMass(135.9045759)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(135-56, 5.8, 0).setAtomicMass(134.9056886)); //Fission not found

		//Most cross sectional data not found
		new BasicElement("Caesium", "Cs", 55, 32.210)
			.addIsotope(new Isotope<BasicElement>(141-55, 0, 0).setAtomicMass(140.920046).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(24.84)))
			.addIsotope(new Isotope<BasicElement>(140-55, 0, 0).setAtomicMass(139.917282).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(63.7)))
			.addIsotope(new Isotope<BasicElement>(139-55, 0, 0).setAtomicMass(138.913364).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(9.27)))
			.addIsotope(new Isotope<BasicElement>(138-55, 0, 0).setAtomicMass(137.911017).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(33.41)))
			.addIsotope(new Isotope<BasicElement>(137-55, 0.27, 0).setAtomicMass(136.9070895).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(30.1671))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(135-55, 0, 0).setAtomicMass(134.9059770).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.3e6))) //Capture = 8.3
			.addIsotope(new Isotope<BasicElement>(133-55, 0, 0).setAtomicMass(132.905451933)); //Capture = 30.3

		new BasicElement("Xenon", "Xe", 54, 21.01)
			.addIsotope(new Isotope<BasicElement>(138-54, 0, 0).setAtomicMass(137.91395).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(14.08))) //Cross sectional data not found
			.addIsotope(new Isotope<BasicElement>(137-54, 0, 0).setAtomicMass(136.911562).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(3.818))) //Cross sectional data not found
			.addIsotope(new Isotope<BasicElement>(136-54, 0.26, 0).setAtomicMass(135.907219).setDecay(new DoubleBetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.165e21))) //Cross sectional fission data not found
			.addIsotope(new Isotope<BasicElement>(135-54, 2650000, 0).setAtomicMass(134.907227).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(9.14))) //Cross sectional fission data not found
			.addIsotope(new Isotope<BasicElement>(134-54, 0.265, 0).setAtomicMass(133.9053945)) //Cross sectional fission data not found
			.addIsotope(new Isotope<BasicElement>(133-54, 190, 0).setAtomicMass(132.9059107).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(5.2475))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(132-54, 0.45, 0).setAtomicMass(131.9041535)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(131-54, 87, 0).setAtomicMass(130.9050824)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(130-54, 4.8, 0).setAtomicMass(129.9035080)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(129-54, 21, 0).setAtomicMass(128.9047794)); //Fission not found

		//Cross sectional data not found
		new BasicElement("Iodine", "I", 53, 54.44/2) //54.22 = MHC of I2
			.addIsotope(new Isotope<BasicElement>(136-53, 0, 0).setAtomicMass(135.91465).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(83.4)))
			.addIsotope(new Isotope<BasicElement>(135-53, 0, 0).setAtomicMass(134.910048).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(6.57)))
			.addIsotope(new Isotope<BasicElement>(134-53, 0, 0).setAtomicMass(133.909744).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(52.5)))
			.addIsotope(new Isotope<BasicElement>(133-53, 0, 0).setAtomicMass(132.907797).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(20.8)))
			.addIsotope(new Isotope<BasicElement>(132-53, 0, 0).setAtomicMass(131.907997).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(2.295)))
			.addIsotope(new Isotope<BasicElement>(129-53, 0, 0).setAtomicMass(128.904988).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.57e7))); //Capture = 30.3

		//Most cross sectional data not found
		new BasicElement("Tellurium", "Te", 52, 25.73)
			.addIsotope(new Isotope<BasicElement>(133-52, 0, 0).setAtomicMass(132.910955).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(12.5)))
			.addIsotope(new Isotope<BasicElement>(132-52, 0, 0).setAtomicMass(131.908553).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(3.204)))
			.addIsotope(new Isotope<BasicElement>(130-52, 0, 0).setAtomicMass(129.9062244).setDecay(new DoubleBetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7.9e20))) //Capture = 0.195
			.addIsotope(new Isotope<BasicElement>(129-52, 0, 0).setAtomicMass(128.9065982).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(69.6)));

		
		//SMALL DECAY PRODUCTS
		new BasicElement("Rhodium", "Rh", 45, 24.98)
			.addIsotope(new Isotope<BasicElement>(103-45, 0, 0).setAtomicMass(102.905504)); //Capture = 143.5

		new BasicElement("Ruthenium", "Ru", 44, 24.06)
			.addIsotope(new Isotope<BasicElement>(104-44, 0, 0).setAtomicMass(103.905433)) //Capture = 0.491
			.addIsotope(new Isotope<BasicElement>(103-44, 1.2, 0).setAtomicMass(102.9063238).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(39.26)))
			.addIsotope(new Isotope<BasicElement>(102-44, 1.27, 0).setAtomicMass(101.9043493))
			.addIsotope(new Isotope<BasicElement>(101-44, 5.2, 0).setAtomicMass(100.9055821))
			.addIsotope(new Isotope<BasicElement>(100-44, 5.8, 0).setAtomicMass(99.9042195))
			.addIsotope(new Isotope<BasicElement>(99-44, 7.24, 0).setAtomicMass(98.9059393));

		//Cross sectional data not found
		new BasicElement("Technetium", "Tc", 43, 24.27)
			.addIsotope(new Isotope<BasicElement>(104-43, 0, 0).setAtomicMass(103.91145).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(18.3)))
			.addIsotope(new Isotope<BasicElement>(103-43, 0, 0).setAtomicMass(102.909181).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(54.2)))
			.addIsotope(new Isotope<BasicElement>(102-43, 0, 0).setAtomicMass(101.909215).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(5.28)))
			.addIsotope(new Isotope<BasicElement>(101-43, 0, 0).setAtomicMass(100.907315).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(14.22)))
			.addIsotope(new Isotope<BasicElement>(100-43, 0, 0).setAtomicMass(99.9076578).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(15.8)))
			.addIsotope(new Isotope<BasicElement>(99-43, 22.8, 0).setAtomicMass(98.9062547).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.111e5))); //Fission not found

		//Cross sectional data not found
		new BasicElement("Molybdenum", "Mo", 42, 24.06)
			.addIsotope(new Isotope<BasicElement>(104-42, 0, 0).setAtomicMass(103.91376).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(60)))
			.addIsotope(new Isotope<BasicElement>(103-42, 0, 0).setAtomicMass(102.91321).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(67.5)))
			.addIsotope(new Isotope<BasicElement>(102-42, 0, 0).setAtomicMass(101.910297).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(11.3)))
			.addIsotope(new Isotope<BasicElement>(101-42, 0, 0).setAtomicMass(100.910347).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(14.61)))
			.addIsotope(new Isotope<BasicElement>(100-42, 0.199, 0).setAtomicMass(99.907477).setDecay(new DoubleBetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(8.5e18))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(99-42, 0, 0).setAtomicMass(98.9077119).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(2.7489))) //Both not found
			.addIsotope(new Isotope<BasicElement>(98-42, 0.13, 0).setAtomicMass(97.9054082)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(97-42, 14.4, 0).setAtomicMass(96.9060215)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(96-42, 0.5, 0).setAtomicMass(95.9046795)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(95-42, 13.4, 0).setAtomicMass(94.9058421)); //Fission not found

		//Cross sectional data not found
		new BasicElement("Niobium", "Nb", 41, 24.60)
			.addIsotope(new Isotope<BasicElement>(104-41, 0, 0).setAtomicMass(103.92246).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(4.9)))
			.addIsotope(new Isotope<BasicElement>(103-41, 0, 0).setAtomicMass(102.91914).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1.5)))
			.addIsotope(new Isotope<BasicElement>(102-41, 0, 0).setAtomicMass(101.91804).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1.3)))
			.addIsotope(new Isotope<BasicElement>(101-41, 0, 0).setAtomicMass(100.915252).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(7.1)))
			.addIsotope(new Isotope<BasicElement>(100-41, 0, 0).setAtomicMass(99.914182).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1.5)))
			.addIsotope(new Isotope<BasicElement>(99-41, 0, 0).setAtomicMass(98.911618).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(15.0)))
			.addIsotope(new Isotope<BasicElement>(98-41, 0, 0).setAtomicMass(97.910328).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(2.86)))
			.addIsotope(new Isotope<BasicElement>(97-41, 0, 0).setAtomicMass(96.9080986).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(72.1)))
			.addIsotope(new Isotope<BasicElement>(96-41, 0, 0).setAtomicMass(95.908101).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(23.35)))
			.addIsotope(new Isotope<BasicElement>(95-41, 7, 0).setAtomicMass(94.9068358).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(34.991))) //Capture <7; 7 used
			.addIsotope(new Isotope<BasicElement>(93-41, 0, 0).setAtomicMass(92.9063781)); //Capture = 1.15

		//Cross sectional data not found
		new BasicElement("Zirconium", "Zr", 40, 25.36)
			.addIsotope(new Isotope<BasicElement>(102-40, 0, 0).setAtomicMass(101.92298).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(2.9)))
			.addIsotope(new Isotope<BasicElement>(101-40, 0, 0).setAtomicMass(100.92114).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(2.3)))
			.addIsotope(new Isotope<BasicElement>(100-40, 0, 0).setAtomicMass(99.91776).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(7.1)))
			.addIsotope(new Isotope<BasicElement>(99-40, 0, 0).setAtomicMass(98.916512).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(2.1))) //Should go to Nb99m
			.addIsotope(new Isotope<BasicElement>(98-40, 0, 0).setAtomicMass(97.912735).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(30.7)))
			.addIsotope(new Isotope<BasicElement>(97-40, 0, 0).setAtomicMass(96.9109531).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(16.744)))
			.addIsotope(new Isotope<BasicElement>(96-40, 0.0229, 0).setAtomicMass(95.9082734).setDecay(new DoubleBetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(20e18))) //Fisison not found
			.addIsotope(new Isotope<BasicElement>(95-40, 0, 0).setAtomicMass(94.9080426).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(64.032))) //All not found
			.addIsotope(new Isotope<BasicElement>(94-40, 0.0494, 0).setAtomicMass(93.9063152)) //Fisison not found
			.addIsotope(new Isotope<BasicElement>(93-40, 4, 0).setAtomicMass(92.9064760).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.53e6))) //Capture < 4; 4 used. Fission not found
			.addIsotope(new Isotope<BasicElement>(92-40, 0.26, 0).setAtomicMass(91.9050408)) //Fisison not found
			.addIsotope(new Isotope<BasicElement>(91-40, 0.83, 0).setAtomicMass(90.9056458)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(90-40, 0.077, 0).setAtomicMass(89.9047044)); //Fission not found

		//Cross sectional data not found
		new BasicElement("Yttrium", "Y", 39, 26.53)
			.addIsotope(new Isotope<BasicElement>(100-39, 0, 0).setAtomicMass(99.92776).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.735)))
			.addIsotope(new Isotope<BasicElement>(99-39, 0, 0).setAtomicMass(98.924636).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1.470)))
			.addIsotope(new Isotope<BasicElement>(98-39, 0, 0).setAtomicMass(97.922203).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.548)))
			.addIsotope(new Isotope<BasicElement>(97-39, 0, 0).setAtomicMass(96.918134).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(3.75)))
			.addIsotope(new Isotope<BasicElement>(96-39, 0, 0).setAtomicMass(95.915891).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(5.34)))
			.addIsotope(new Isotope<BasicElement>(95-39, 0, 0).setAtomicMass(94.912821).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(10.3)))
			.addIsotope(new Isotope<BasicElement>(94-39, 0, 0).setAtomicMass(93.911595).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(18.7)))
			.addIsotope(new Isotope<BasicElement>(93-39, 0, 0).setAtomicMass(92.909583).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(10.18)))
			.addIsotope(new Isotope<BasicElement>(92-39, 0, 0).setAtomicMass(91.908949).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(3.54)))
			.addIsotope(new Isotope<BasicElement>(91-39, 1.4, 0).setAtomicMass(90.907305).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(58.51)))
			.addIsotope(new Isotope<BasicElement>(90-39, 6.5, 0).setAtomicMass(89.9071519).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(64.053))) //Capture < 6.5; 6.5 used
			.addIsotope(new Isotope<BasicElement>(89-39, 1.28, 0).setAtomicMass(88.9058483)); //Fission not found

		//Cross sectional data not found
		new BasicElement("Strontium", "Sr", 38, 26.4)
			.addIsotope(new Isotope<BasicElement>(98-38, 0, 0).setAtomicMass(97.928453).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.653)))
			.addIsotope(new Isotope<BasicElement>(97-38, 0, 0).setAtomicMass(96.926153).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.429)))
			.addIsotope(new Isotope<BasicElement>(96-38, 0, 0).setAtomicMass(95.921697).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1.07)))
			.addIsotope(new Isotope<BasicElement>(95-38, 0, 0).setAtomicMass(94.919359).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(23.90)))
			.addIsotope(new Isotope<BasicElement>(94-38, 0, 0).setAtomicMass(93.915361).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(75.3)))
			.addIsotope(new Isotope<BasicElement>(93-38, 0, 0).setAtomicMass(92.914026).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(7.423)))
			.addIsotope(new Isotope<BasicElement>(92-38, 0, 0).setAtomicMass(91.911038).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(2.66)))
			.addIsotope(new Isotope<BasicElement>(91-38, 0, 0).setAtomicMass(90.910203).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(9.63)))
			.addIsotope(new Isotope<BasicElement>(90-38, 0.00104, 0).setAtomicMass(89.907738).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(28.90))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(89-38, 0.42, 0).setAtomicMass(88.9074507).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(50.57))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(88-38, 0.0058, 0).setAtomicMass(87.9056122571)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(87-38, 17, 0).setAtomicMass(86.9088774970)); //Fission not found

		//Cross sectional data not found
		new BasicElement("Rubidium", "Rb", 37, 31.060)
			.addIsotope(new Isotope<BasicElement>(95-37, 0, 0).setAtomicMass(94.929303).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.3775)))
			.addIsotope(new Isotope<BasicElement>(94-37, 0, 0).setAtomicMass(93.926405).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(2.702)))
			.addIsotope(new Isotope<BasicElement>(93-37, 0, 0).setAtomicMass(92.922042).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(5.84)))
			.addIsotope(new Isotope<BasicElement>(92-37, 0, 0).setAtomicMass(91.919729).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(4.492)))
			.addIsotope(new Isotope<BasicElement>(91-37, 0, 0).setAtomicMass(90.916537).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(58.4)))
			.addIsotope(new Isotope<BasicElement>(90-37, 0, 0).setAtomicMass(89.914802).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(158)))
			.addIsotope(new Isotope<BasicElement>(89-37, 0, 0).setAtomicMass(88.912278).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(15.15)))
			.addIsotope(new Isotope<BasicElement>(88-37, 0, 0).setAtomicMass(87.91131559).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(17.773)))
			.addIsotope(new Isotope<BasicElement>(87-37, 0.116, 0).setAtomicMass(86.909180527).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(4.923e10))) //Fission not found
			.addIsotope(new Isotope<BasicElement>(85-37, 0, 0).setAtomicMass(84.911789738)); //Capture = 0.494

		//Cross sectional data not found
		new BasicElement("Krypton", "Kr", 36, 20.95)
			.addIsotope(new Isotope<BasicElement>(93-36, 0, 0).setAtomicMass(92.93127).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1.286)))
			.addIsotope(new Isotope<BasicElement>(92-36, 0, 0).setAtomicMass(91.926156).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1.840)))
			.addIsotope(new Isotope<BasicElement>(91-36, 0, 0).setAtomicMass(90.92345).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(8.57)))
			.addIsotope(new Isotope<BasicElement>(90-36, 0, 0).setAtomicMass(89.919517).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(32.32))) //Should go to Rb90m
			.addIsotope(new Isotope<BasicElement>(89-36, 0, 0).setAtomicMass(88.91763).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(3.15)))
			.addIsotope(new Isotope<BasicElement>(88-36, 0, 0).setAtomicMass(87.914447).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(2.84)))
			.addIsotope(new Isotope<BasicElement>(87-36, 0, 0).setAtomicMass(86.91335486).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(76.3)))
			.addIsotope(new Isotope<BasicElement>(86-36, 0.003, 0).setAtomicMass(85.91061073)) //Fission not found
			.addIsotope(new Isotope<BasicElement>(85-36, 1.66, 0).setAtomicMass(84.9125273).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(10.776))); //Fission not found

		//Cross sectional data not found
		new BasicElement("Bromine", "Br", 35, 75.69/2) // 75.69 = Molar heat capacity for Br2 so i decided to divide by 2 for molar heat capacity of Br
			.addIsotope(new Isotope<BasicElement>(90-35, 0, 0).setAtomicMass(89.93063).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1.91)))
			.addIsotope(new Isotope<BasicElement>(89-35, 0, 0).setAtomicMass(88.92639).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(4.40)))
			.addIsotope(new Isotope<BasicElement>(88-35, 0, 0).setAtomicMass(87.92407).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(16.29)))
			.addIsotope(new Isotope<BasicElement>(87-35, 0, 0).setAtomicMass(86.920711).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(55.65)))
			.addIsotope(new Isotope<BasicElement>(86-35, 0, 0).setAtomicMass(85.918798).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(55.1)))
			.addIsotope(new Isotope<BasicElement>(85-35, 0, 0).setAtomicMass(84.915608).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(2.90)));

		//Cross sectional data not found
		new BasicElement("Selenium", "Se", 34, 25.363)
			.addIsotope(new Isotope<BasicElement>(87-34, 0, 0).setAtomicMass(86.92852).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(5.50)))
			.addIsotope(new Isotope<BasicElement>(86-34, 0, 0).setAtomicMass(85.924272).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(15.3)))
			.addIsotope(new Isotope<BasicElement>(85-34, 0, 0).setAtomicMass(84.92225).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(31.7)));

		new BasicElement("Helium", "He", 2, 20.78)
			.addIsotope(new Isotope<BasicElement>(4-2, 0, 0).setAtomicMass(4.00260325415)); //Cross sections not found
	}
}

