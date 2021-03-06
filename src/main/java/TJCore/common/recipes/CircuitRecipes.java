package TJCore.common.recipes;

import TJCore.api.TJLog;
import TJCore.common.metaitem.TJMetaItems;
import TJCore.common.recipes.recipemaps.TJRecipeMaps;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.ModHandler;

import static TJCore.common.recipes.recipemaps.TJRecipeMaps.PRINTER_RECIPES;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;

import static TJCore.common.metaitem.TJMetaItems.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;

public class CircuitRecipes {

    /*
        CURRENT CIRCUIT PROGRESSION
            Primitive (steam)
            Electrical (lv)
            Integrated (mv)
            Micro (hv)
            Nano (ev)
            Imc (iv)
            Optical (luv)
            Quantum (zpm)

        OpV skews
        Uxv skews
        Uiv skews are wetware mainframe, quantum computer, planck assembly and cosmic processor
        Uev skews are bioware mainframe, wetware computer, quantum assembly and planck processor
        Uhv skews are crystal mainframe, bioware computer, wetware assembly and quantum processor
        Uv skews are optical mainframe, crystal computer, bioware assembly and wetware processor
        Zpm skews are imc mainframe, optical computer, crystal assembly and bioware processor
        Luv skews are nano mainframe, imc computer, optical assembly and crystal processor
        Iv skews are micro mainframe, nano computer, imc assembly and optical processor
        Ev skews are integrated mainframe, micro computer, nano assembly and imc processor
        Hv skews are electronic mainframe, integrated computer, micro assembly and nano processor
        Mv are primitive mainframe, electronic computer, integrated assembly and micro processor
        Lv is primitive computer, electronic assembly and integrated processor
        Ulv is primitive assembly and electronic processor
    */
    /*
        mainframes should have some kinda heatsink component


     */

    public static void removePreexistingCircuits() {
        ELECTRONIC_CIRCUIT_LV.setInvisible();
    }

    public static void registerCircuits() {

        PrimitiveElectronicIntegratedLines();
    }

    public static void PrimitiveElectronicIntegratedLines() {

        ModHandler.addShapelessRecipe("capacitor", CAPACITOR.getStackForm(3),
                new UnificationEntry(plate, Nickel),
                new UnificationEntry(dust, Sulfur),
                new UnificationEntry(dust, Mica),
                new UnificationEntry(wireGtSingle, Copper));


        ModHandler.addShapedRecipe("primitive_assembly", PRIMITIVE_ASSEMBLY_ULV.getStackForm(),
                "RVR", "WBW", " V ",
                'R', RESISTOR.getStackForm(),
                'V', VACUUM_TUBE.getStackForm(),
                'B', COATED_BOARD.getStackForm(),
                'W', new UnificationEntry(wireGtSingle, Tin));

        ModHandler.addShapedRecipe("primitive_assembly", PRIMITIVE_ASSEMBLY_ULV.getStackForm(),
                "RVR", "WBW", " V ",
                'R', RESISTOR.getStackForm(),
                'V', VACUUM_TUBE.getStackForm(),
                'B', PHENOLIC_BOARD.getStackForm(),
                'W', new UnificationEntry(wireGtSingle, Tin));

        ModHandler.addShapedRecipe("primitive_computer", PRIMITIVE_COMPUTER_LV.getStackForm(),
                "CFC", "APA", "FAF",
                'P', new UnificationEntry(plate, Steel),
                'C', CAPACITOR.getStackForm(),
                'F', new UnificationEntry(wireFine, Copper),
                'A', PRIMITIVE_ASSEMBLY_ULV.getStackForm());

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(PRIMITIVE_COMPUTER_LV.getStackForm(2))
                .input(plate, Aluminium, 2)
                .input(DIODE, 2)
                .input(INDUCTOR, 2)
                .output(PRIMITIVE_MAINFRAME_MV)
                .EUt(30)
                .duration(15 * 20)
                .buildAndRegister();






        MetaItem<?>.MetaValueItem[] boards = {GOOD_CIRCUIT_BOARD, PLASTIC_CIRCUIT_BOARD};
        MetaItem<?>.MetaValueItem[] transistor = {TRANSISTOR, SMD_TRANSISTOR_1};
        MetaItem<?>.MetaValueItem[] resistor = {RESISTOR, SMD_RESISTOR_1};
        MetaItem<?>.MetaValueItem[] diode = {DIODE, SMD_DIODE_1};
        MetaItem<?>.MetaValueItem[] capacitor = {CAPACITOR, SMD_CAPACITOR_1};
        MetaItem<?>.MetaValueItem[] inductor = {INDUCTOR, SMD_INDUCTOR_1};
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                        .input(boards[i])
                        .input(NAND_MEMORY_CHIP, 2)
                        .input(resistor[j], 4)
                        .input(wireFine, Tin, 4)
                        .output(ELECTRONIC_PROCESSOR_ULV)
                        .EUt(30)
                        .duration(50)
                        .buildAndRegister();


            }
            CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ELECTRONIC_PROCESSOR_ULV, 3)
                    .input(capacitor[i], 2)
                    .input(transistor[i], 2)
                    .input(resistor[i], 4)
                    .input(wireFine, TinAlloy, 8)
                    .output(ELECTRONIC_ASSEMBLY_LV)
                    .EUt(30)
                    .duration(100)
                    .buildAndRegister();

            CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ELECTRONIC_ASSEMBLY_LV, 2)
                    .input(inductor[i], 2)
                    .input(transistor[i], 4)
                    .input(resistor[i], 2)
                    .input(wireFine, Copper, 8)
                    .output(ELECTRONIC_COMPUTER_MV)
                    .EUt(30)
                    .duration(200)
                    .buildAndRegister();

            CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ELECTRONIC_COMPUTER_MV, 2)
                    .input(diode[i], 2)
                    .input(inductor[i], 4)
                    .input(frameGt, StainlessSteel, 1)
                    .output(ELECTRONIC_MAINFRAME_HV)
                    .EUt(30)
                    .duration(400)
                    .buildAndRegister();


        }
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(PLASTIC_CIRCUIT_BOARD)
                .input(SYSTEM_ON_CHIP, 1)
                .input(wireFine, Copper, 4)
                .output(ELECTRONIC_PROCESSOR_ULV, 3)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();


       PRINTER_RECIPES.recipeBuilder()
               .input(wireFine, Polyethylene, 32)
               .outputs(LITHOGRAPHY_MASK.getStackForm(Polyethylene))
               .EUt(4)
               .duration(4)
               .buildAndRegister();



       //LASER_ENGRAVER_RECIPES.recipeBuilder()
       //        .input(LITHOGRAPHY_MASK.getStackForm(Polyethylene))



        // TODO(Onion): add to cleanroom logic recipes

        MetaItem<?>.MetaValueItem[] board2 = { PLASTIC_CIRCUIT_BOARD };

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(board2[0])
                .input(SMD_TRANSISTOR_2, 2)
                .input(SMD_CAPACITOR_2, 2)
                .input(SMD_RESISTOR_2, 4)
                .input(INTEGRATED_LOGIC_CIRCUIT) // needs lithography shit
                .output(INTEGRATED_PROCESSOR_LV)
                .EUt(VA[MV])
                .duration(50)
                .buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(INTEGRATED_PROCESSOR_LV, 3)
                .input(INTEGRATED_LOGIC_CIRCUIT)
                .input(SMD_CAPACITOR_2, 2)
                .input(SMD_RESISTOR_2, 4)
                .input(plate, Aluminium)
                .input(wireFine, Copper, 8)
                .output(INTEGRATED_ASSEMBLY_MV)
                .EUt(VA[MV])
                .duration(90)
                .buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(INTEGRATED_ASSEMBLY_MV, 2)
                .input(SMD_TRANSISTOR_2, 2)
                .input(SMD_INDUCTOR_2, 4)
                .input(plate, StainlessSteel, 2)
                .input(cableGtSingle, Gold, 2)
                .output(INTEGRATED_COMPUTER_HV)
                .EUt(VA[MV])
                .duration(180)
                .buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(INTEGRATED_COMPUTER_HV, 2)
                .input(SMD_DIODE_2, 2)
                .input(SMD_INDUCTOR_2, 4)
                .input(RANDOM_ACCESS_MEMORY, 3)
                .input(plate, Titanium, 2)
                .input(cableGtDouble, Aluminium, 1)
                .output(INTEGRATED_MAINFRAME_EV)
                .EUt(VA[MV])
                .duration(270)
                .buildAndRegister();
    }

    public static void MicroLine() {




    }

    public static void NanoLine() {

    }

    public static void IMCLine() {

    }

    public static void OpticalLine() {

    }

    public static void CrystalLine() {

    }

    public static void BiowareLine() {

    }

    public static void WetwareLine() {

    }





}
